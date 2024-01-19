package com.shuangyang.flutter_yunmou;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

//import com.videogo.openapi.EZOpenSDK;
//import com.videogo.openapi.EZPlayer;
//import com.videogo.openapi.bean.EZDeviceInfo;

import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
/** FlutterYunmouPlugin */
public class FlutterYunmouPlugin implements FlutterPlugin {
//  /// The MethodChannel that will the communication between Flutter and native Android
//  ///
//  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
//  /// when the Flutter Engine is detached from the Activity
//  private MethodChannel channel;
//  private Context applicationContext;
//  private Activity activity;
//
//  @Override
//  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
//    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_yunmou");
//    channel.setMethodCallHandler(this);
//    applicationContext = flutterPluginBinding.getApplicationContext();
//  }
//
//  @Override
//  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
//    if (call.method.equals("getPlatformVersion")) {
//      result.success("Android " + android.os.Build.VERSION.RELEASE);
//    }
//    else if (call.method.equals("initSDK")) {
//      final String oauthToken = "97d8d93a-8973-42b2-b140-7133819be2db";
//      CloudOpenSDK.getInstance()
//              .setLogDebugMode(true) // 默认日志开关状态：打开
//              //sdk数据缓存加密开关（例如SP存储），放在init()方法前设置
//              //isEncrypt,true:开启加密,false:不加密
//              .setDataCacheEncrypt(false, "123456")//密码长度不限制
//              .init(
//                      activity,
//                      oauthToken,
//                      new OnCommonCallBack() {
//                        @Override
//                        public void onSuccess() {
//                          Toast.makeText(applicationContext, "SDK初始化成功", Toast.LENGTH_SHORT).show();
//                          result.success(true);
//                        }
//
//                        @Override
//                        public void onFailed(Exception e) {
//                          Toast.makeText(applicationContext, "SDK初始化失败", Toast.LENGTH_SHORT).show();
//                          result.success(false);
//                        }
//                      });
////      result.success(true);
//    }
//    else {
//      result.notImplemented();
//    }
//  }
//
//  @Override
//  public void onDetachedFromActivity() {
//    // TODO("Not yet implemented");
//  }
//
//  @Override
//  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
//    // TODO("Not yet implemented");
//  }
//
//  @Override
//  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
//    activity = binding.getActivity();
//  }
//
//  @Override
//  public void onDetachedFromActivityForConfigChanges() {
//    // TODO("Not yet implemented");
//  }
//
//
//  @Override
//  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
//    channel.setMethodCallHandler(null);
//  }

  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
//  private MethodChannel channel;
//  private Application application;
//
//  private SurfaceView mRealPlaySv;
//  private SurfaceHolder mRealPlaySh;
//  private EZPlayer mEZPlayer = null;
  //  private Application application;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
    // application = (Application) binding.getApplicationContext();
    // binding.getPlatformViewRegistry().registerViewFactory(
    //   "cspy/flutter_eaviz/videoView",
    //   new FlutterEzvizViewFactory(binding.getBinaryMessenger(), application)
    // );
//    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_ezviz");
//    channel.setMethodCallHandler(this);
//    application = (Application) flutterPluginBinding.getApplicationContext();
  }

//  @Override
//  public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {

//    if (methodCall.method.equals("getPlatformVersion")) {
//      String accessToken = methodCall.argument("accessToken");
//      String APP_KEY = methodCall.argument("appKey");
//      String deviceSN = methodCall.argument("deviceSN");
//      Integer cameraNo = methodCall.argument("cameraNo");
//      Log.d("Tag", accessToken);
//      /** * sdk日志开关，正式发布需要去掉 */
//      EZOpenSDK.showSDKLog(true);
//      /** * 设置是否支持P2P取流,详见api */
//      EZOpenSDK.enableP2P(false);
//
//      /** * APP_KEY请替换成自己申请的 */
//      EZOpenSDK.initLib(application, APP_KEY);
//
//      EZOpenSDK.getInstance().setAccessToken(accessToken);
//      mEZPlayer  = EZOpenSDK.getInstance().createPlayer(deviceSN, cameraNo);
////      mEZPlayer.setHandler(mHandler);
//
////      mEZPlayer.setSurfaceHold(new SurfaceHolder(surfaceView.getHolder()));
//
//      mEZPlayer.startRealPlay();
//
////      tvPlay.setEnabled(true);
//
//      result.success("Android " + android.os.Build.VERSION.RELEASE);
//    } else if (methodCall.method.equals("logout")) {
//      EZOpenSDK.getInstance().logout();
//    } else {
//      result.notImplemented();
//    }
//  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
//    channel.setMethodCallHandler(null);
  }
}
