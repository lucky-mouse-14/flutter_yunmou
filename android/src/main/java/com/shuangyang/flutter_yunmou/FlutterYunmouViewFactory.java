package com.shuangyang.flutter_yunmou;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hikvision.cloud.sdk.CloudOpenSDK;
import com.hikvision.cloud.sdk.core.OnCommonCallBack;

import io.flutter.Log;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;
import io.flutter.plugin.common.StandardMessageCodec;

public class FlutterYunmouViewFactory extends PlatformViewFactory implements MethodChannel.MethodCallHandler {
    private static final String CHANNEL = "flutter_yunmou/plugin";
    @NonNull private final BinaryMessenger messenger;
    private Application application;
    private Activity activity;

    public FlutterYunmouViewFactory(@NonNull BinaryMessenger messenger, Application application, Activity activity) {
        super(StandardMessageCodec.INSTANCE);
        this.messenger = messenger;
        this.application = application;
        this.activity = activity;

        new MethodChannel(messenger, CHANNEL).setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if (call.method.equals("initSDK")) {
            String oauthToken = call.argument("authToken");
            CloudOpenSDK.getInstance()
                    .setLogDebugMode(true) // 默认日志开关状态：打开
                    //sdk数据缓存加密开关（例如SP存储），放在init()方法前设置
                    //isEncrypt,true:开启加密,false:不加密
                    .setDataCacheEncrypt(false, "123456")//密码长度不限制
                    .init(
                            this.application,
                            oauthToken,
                            new OnCommonCallBack() {
                                @Override
                                public void onSuccess() {
                                    result.success(true);
                                }

                                @Override
                                public void onFailed(Exception e) {
                                    result.success(false);
                                }
                            });
        }
    }

    @NonNull
    @Override
    public PlatformView create(@Nullable Context context, int id, @Nullable Object args) {
        return new FlutterYunmouView(context, messenger, application, activity);
    }

}
