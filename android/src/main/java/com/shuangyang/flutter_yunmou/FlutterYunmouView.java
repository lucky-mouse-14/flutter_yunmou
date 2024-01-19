package com.shuangyang.flutter_yunmou;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hikvision.cloud.sdk.CloudOpenSDK;
import com.hikvision.cloud.sdk.core.CloudOpenSDKListener;
import com.hikvision.cloud.sdk.core.CloudVideoPlayer;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.openapi.EZConstants;
//import com.hikvision.cloud.util.ScreenOrientationHelper;
import com.hikvision.cloud.util.RxUtils;
import com.videogo.exception.BaseException;

import io.flutter.Log;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DisposableObserver;

public class FlutterYunmouView implements PlatformView, MethodChannel.MethodCallHandler {
    private static final String CHANNEL = "flutter_yunmou";
    @NonNull private final BinaryMessenger messenger;
    private Application application;

    private SurfaceView mSurfaceView;

    private boolean isPlayOpenStatus;
    private boolean isOldPlaying; // 用于界面不可见和可见切换时，记录是否预览的状态
    private boolean isSoundOpenStatus;
    private int mVideoLevel;
    private boolean isRecordOpenStatus;
    private boolean isTalkOpenStatus;

    private String mDeviceSerial; // 设备序列号
    private int mChannelNo; // 通道号

    private CloudVideoPlayer mRealPlayer = null;
    private CloudVideoPlayer mTalkPlayer = null; // 预览和对讲用两个player，避免有回应、啸叫
    private EZDeviceInfo mDeviceInfo;
//    private ScreenOrientationHelper mScreenOrientationHelper = null;// 转屏控制器

    private boolean isEncry = false;
    private EZConstants.EZTalkbackCapability mTalkAbility; //设备对讲信息

    private boolean isHolderFirstCreated = true;

    private RxPermissions mRxPermissions;

    private Disposable mPlayerDeviceInfoDisposable;

    BasicMessageChannel<Object> nativeToFlutterYunmou;
//    List<EZDeviceRecordFile> ezDeviceRecordFiles = null;
    Object queryVideoLock = new Object();

    FlutterYunmouView(@NonNull Context context, BinaryMessenger messenger, Application application, Activity activity) {
        this.messenger = messenger;
        this.application = application;
        new MethodChannel(messenger, CHANNEL).setMethodCallHandler(this);

        nativeToFlutterYunmou = new BasicMessageChannel<>(messenger, "nativeToFlutterYunmou", new StandardMessageCodec());

        mSurfaceView = new SurfaceView(context);

        mRxPermissions = new RxPermissions(activity);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if (call.method.equals("initPlayer")) {
            mDeviceSerial = call.argument("deviceSerial");
            mChannelNo = call.argument("channelNo");

            getDeviceInfo();

            result.success(true);
        }
        else if (call.method.equals("startRealPlay")) {
            mRealPlayer.startRealPlay();
            // 获取权限
            mRxPermissions.requestEach(Manifest.permission.RECORD_AUDIO)
                    .subscribe(permission -> {
                        if (permission.granted) {
                            if (mTalkAbility == EZConstants.EZTalkbackCapability.EZTalkbackFullDuplex) {
                                if (null == mTalkPlayer) {
                                    return;
                                }
//                                mTalkPlayer.setOnVoicTalkListener(onVoiceTalkListener);
                                mTalkPlayer.startVoiceTalk();
                            } else if (mTalkAbility == EZConstants.EZTalkbackCapability.EZTalkbackHalfDuplex) {
//                                showHalfVideoTalkPopupWindow();
                            } else {
                                System.out.println("该设备不支持对讲功能");
                            }
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            System.out.println("对讲开启失败，拒绝权限，等待下次询问哦");
                        } else {
                            System.out.println("对讲开启失败，不再弹出询问框，请前往APP应用设置中打开此权限");
                        }
                    });
//            mTalkPlayer.startVoiceTalk();
            result.success(true);
        }
        else if (call.method.equals("stopRealPlay")) {
            stopPlay();
            result.success(true);
        }
        else if (call.method.equals("sound")) {
            Boolean isOpen = call.argument("open");
            System.out.println("??????????????????????" + isOpen);
            if (isOpen) {
                mRealPlayer.openSound();
                isSoundOpenStatus = true;
                mTalkPlayer.startVoiceTalk();
                isTalkOpenStatus = true;
            }
            else {
                mRealPlayer.closeSound();
                isSoundOpenStatus = false;
                mTalkPlayer.stopVoiceTalk();
                isTalkOpenStatus = false;
            }
            result.success(true);
        }
        else if (call.method.equals("release")) {
            mRealPlayer.release();
            result.success(true);
        }
        else {
            result.notImplemented();
        }
    }

    /**
     * 开始预览
     * @param isEncry 是否加密，加密的话，设置设备验证码
     * @return
     */
    private void initPlayer(boolean isEncry) {
        mRealPlayer = CloudOpenSDK.getInstance().createPlayer(mDeviceSerial, mChannelNo);
        mRealPlayer.setSurfaceHolder(mSurfaceView.getHolder());
        // mTalkPlayer = CloudOpenSDK.getInstance().createPlayer(mDeviceSerial, mChannelNo);
        // mTalkPlayer.setOnVoicTalkListener(onVoiceTalkListener);
        if (isEncry) {

        }
        mRealPlayer.closeSound();
        // mRealPlayer.startRealPlay();
        mRealPlayer.setOnRealPlayListener(new CloudOpenSDKListener.OnRealPlayListener() {
            @Override
            public void onVideoSizeChanged(int videoWidth, int videoHeight) {

            }

            @Override
            public void onRealPlaySuccess() {
//                mScreenOrientationHelper.enableSensorOrientation();
                isPlayOpenStatus = true;
                // 默认开启声音
                if (mRealPlayer.openSound()) {
                    isSoundOpenStatus = true;
                }
            }

            @Override
            public void  onStopRealPlaySuccess() {
                isPlayOpenStatus = false;
                isSoundOpenStatus = false;

                //释放player的资源(player需要重新创建时，最好把前一个player进行释放操作)
                //mRealPlayer.release();
            }

            /**
             * 播放失败回调，得到失败信息
             *
             * @param errorCode      播放失败错误码
             * @param moduleCode  播放失败模块错误码
             * @param description     播放失败描述
             * @param sulution          播放失败解决方案
             */
            @Override
            public void onRealPlayFailed(int errorCode, String moduleCode, String description, String sulution) {
                System.out.println("**** errorCode:" + errorCode);

                isPlayOpenStatus = false;
                isSoundOpenStatus = false;
                if (errorCode == 400035 || errorCode == 400036) {
                    //回调时查看errorCode，如果为400035（需要输入验证码）和400036（验证码错误），
                    // 则需要开发者自己处理让用户重新输入验证密码，并调用setPlayVerifyCode设置密码，
                    // 然后重新启动播放
                    return;
                }

                stopPlay();
            }
        });

        // 设置播放器的显示Surface
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                if (mRealPlayer != null) {
                    mRealPlayer.setSurfaceHolder(holder);
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                if (mRealPlayer != null) {
                    mRealPlayer.setSurfaceHolder(null);
                }
//                    mRealPlaySh = null;

            }
        });
    }

    private void initTalker(boolean isEncry) {
        mTalkPlayer = CloudOpenSDK.getInstance().createPlayer(mDeviceSerial, mChannelNo);
        mTalkPlayer.setOnVoicTalkListener(new CloudOpenSDKListener.OnVoiceTalkListener() {
            @Override
            public void onStartVoiceTalkSuccess() {
                System.out.println("111111111111");
                isTalkOpenStatus = true;
                // 对讲的时候需要关闭预览player的声音
                if (isSoundOpenStatus) {
                    isSoundOpenStatus = false;
                    mRealPlayer.closeSound();
                }
                // 如果为半双工的情况下，设置pressed状态
                if (mTalkAbility == EZConstants.EZTalkbackCapability.EZTalkbackHalfDuplex) {
                    mTalkPlayer.setVoiceTalkStatus(true);
                }
                // TODO
                //mRealPlayer.setSpeakerphoneOn(true);
            }

            @Override
            public void onStopVoiceTalkSuccess() {
                System.out.println("2222222222222");
                // 停止对讲成功
                isTalkOpenStatus = false;
                if (!isSoundOpenStatus) {
                    isSoundOpenStatus = true;
                    mRealPlayer.openSound();
                }

                //释放player的资源
                //mTalkPlayer.release();
            }

            /**
             * 对讲失败回调,得到失败信息
             *
             * @param errorCode   播放失败错误码
             * @param moduleCode  播放失败模块错误码
             * @param description 播放失败描述
             * @param sulution    播放失败解决方方案
             */
            @Override
            public void onVoiceTalkFail(int errorCode, String moduleCode, String description, String sulution) {
                System.out.println("**** errorCode:" + errorCode);
                //开启对讲失败或停止对讲失败，这里需要开发者自己去判断是开启操作还是停止的操作
                //停止对讲失败后，不影响下一次的start使用
                // TODO
//            toast(description);
            }
        });
    }

    private void stopPlay() {
        //mScreenOrientationHelper.disableSensorOrientation();
        if (null != mRealPlayer) {
            mRealPlayer.closeSound();
            mRealPlayer.stopRealPlay(); // 停止播放
            if (isTalkOpenStatus) {
                mTalkPlayer.stopVoiceTalk(); //停止对讲
            }
        }
    }

    @Override
    public View getView() {
        return mSurfaceView;
    }

    @Override
    public void dispose() {
        if(null != mRealPlayer) {
            mRealPlayer.release();
        }
    }

    /**
     * CloudOpenSDK.getEZDeviceInfo()需要在子线程中调用
     */
    private void getDeviceInfo() {
        System.out.println("getDeviceInfo");
        mPlayerDeviceInfoDisposable = Observable.create((ObservableOnSubscribe<EZDeviceInfo>) emitter -> {
                    EZDeviceInfo deviceInfo = CloudOpenSDK.getEZDeviceInfo(mDeviceSerial);
                    if (null != deviceInfo) {
                        emitter.onNext(deviceInfo);
                    } else {
                        emitter.onError(new Throwable());
                    }
                    emitter.onComplete();
                }).compose(RxUtils.io2Main())
                .subscribeWith(new DisposableObserver<EZDeviceInfo>() {

                    @Override
                    public void onNext(EZDeviceInfo deviceInfo) {
                        mDeviceInfo = deviceInfo;
                        // 获取对讲信息,对讲模式类型:
                        // 不支持对讲:EZConstants.EZTalkbackCapability.EZTalkbackNoSupport
                        // 支持全双工对讲:EZConstants.EZTalkbackCapability.EZTalkbackFullDuplex
                        // 支持半双工对讲:EZConstants.EZTalkbackCapability.EZTalkbackHalfDuplex
                        mTalkAbility = mDeviceInfo.isSupportTalk();
                        System.out.println("******* mTalkAbility:"+ mTalkAbility);
                        initPlayer(isEncry);
                        initTalker(isEncry);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof BaseException) {
//                            toast(e.getMessage());
                        }
                        if (mPlayerDeviceInfoDisposable != null && !mPlayerDeviceInfoDisposable.isDisposed()) {
                            mPlayerDeviceInfoDisposable.dispose();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (mPlayerDeviceInfoDisposable != null && !mPlayerDeviceInfoDisposable.isDisposed()) {
                            mPlayerDeviceInfoDisposable.dispose();
                        }
                    }
                });
    }
}
