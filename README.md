# flutter_yunmou

flutter 海康云眸SDK插件

#### 实现功能

android 实时预览+对讲


#### 安装

添加依赖 `flutter_yunmou 到 pubspec.yaml 文件

#### 运行

```bash
flutter pub get
```

#### android权限

在 android/app/src/main/AndroidMainfest.xml 文件中添加权限:
```
    <uses-permission
        android:name="android.permission.READ_PHONE_STATE"/>
    <uses-permission
        android:name="android.permission.INTERNET"/>
    <uses-permission
        android:name="android.permission.CAMERA"/>
    <uses-permission
        android:name="android.permission.RECORD_AUDIO"/>
    <uses-permission
        android:name="android.permission.MODIFY_AUDIO_SETTINGS"/>
    <uses-permission
        android:name="android.permission.VIDEO_CAPTURE"/>
    <uses-permission
        android:name="android.permission.AUDIO_CAPTURE"/>
    <uses-permission
        android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission
        android:name="android.permission.ACCESS_WIFI_STATE"/>
    <uses-permission
        android:name="android.permission.QUERY_ALL_PACKAGES"/>
    <!-- 基础功能所需权限 -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <!-- 配网所需权限 -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_MULTICAST_STATE" />
    <!-- 读取权限 选择本地相册-->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <!-- 存入权限 需要把拍摄的照片或视频存入-->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WAKE_LOCK"/>
    <!-- 网络定位 -->
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    <!-- 麦克风权限-->
    <uses-permission android:name="android.permission.RECORD_AUDIO"/>
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS"/>

    <uses-permission android:name="android.permission.CAMERA" /> <!-- 音视频通话权限所需 -->
```

#### kotlin 修改MainActivity.kt

```
package com.example.shuangyang

// import io.flutter.embedding.android.FlutterActivity

import androidx.annotation.NonNull;

import com.shuangyang.flutter_yunmou.FlutterYunmouViewFactory;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;

class MainActivity : FlutterActivity() {
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        flutterEngine.platformViewsController.registry.registerViewFactory(
            "flutter_yunmou/videoView",
            FlutterYunmouViewFactory(
                flutterEngine.dartExecutor.binaryMessenger,
                application,
                this
            )
        )
    }
}

```


#### Java 修改MainActivity.java

```
package com.shuangyang.flutter_yunmou_example;

//import io.flutter.embedding.android.FlutterActivity;

import androidx.annotation.NonNull;

import com.shuangyang.flutter_yunmou.FlutterYunmouViewFactory;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;

//public class MainActivity extends FlutterActivity {
//}
public class MainActivity extends FlutterActivity {
    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        flutterEngine.getPlatformViewsController().getRegistry().registerViewFactory("flutter_yunmou/videoView", new FlutterYunmouViewFactory(flutterEngine.getDartExecutor().getBinaryMessenger(), getApplication(), this));
    }
}

```

#### 代码混淆

sdk打包不能混淆，android/app 下添加文件 proguard-rules.pro
```
-dontwarn com.ezviz.**
-keep class com.ezviz.** { *;}

-dontwarn com.ez.**
-keep class com.ez.** { *;}

-dontwarn com.hc.**
-keep class com.hc.** { *;}

-dontwarn com.neutral.netsdk.**
-keep class com.neutral.netsdk.** { *;}

-dontwarn com.hik.**
-keep class com.hik.** { *;}

-dontwarn com.hikvision.audio.**
-keep class com.hikvision.audio.** { *;}

-dontwarn com.hikvision.keyprotect.**
-keep class com.hikvision.keyprotect.** { *;}

-dontwarn com.hikvision.sadp.**
-keep class com.hikvision.sadp.** { *;}

-dontwarn com.hikvision.netsdk.**
-keep class com.hikvision.netsdk.** { *;}

-dontwarn com.hikvision.wifi.**
-keep class com.hikvision.wifi.** { *;}

-dontwarn com.hikvision.cloud.sdk.**
-keep class com.hikvision.cloud.sdk.** { *;}

-dontwarn com.videogo.**
-keep class com.videogo.** { *;}

-dontwarn okhttp3.**
-keep class okhttp3.** { *;}

-dontwarn com.mediaplayer.audio.**
-keep class com.mediaplayer.audio.** { *;}

-dontwarn org.MediaPlayer.PlayM4.**
-keep class org.MediaPlayer.PlayM4.** { *;}

-dontwarn com.sun.jna.**
-keep class com.sun.jna.**{*;}

#Gson混淆配置
-keepattributes Annotation
-keep class sun.misc.Unsafe { *; }
-keep class com.idea.fifaalarmclock.entity.*
-keep class com.google.gson.stream.* { *; }

#引用mars的xlog，混淆配置
-keep class com.tencent.mars.** {
 public protected private *;
}


#okhttp okio
-dontwarn okio.**
-keep class okio.** { *;}

#Rxjava RxAndroid

-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
long producerIndex;
long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#eventbus 3.0
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
```

#### 使用方法

```dart
import 'package:flutter/material.dart';

import 'package:flutter/services.dart';
import 'package:flutter_yunmou/flutter_yunmou.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String authToken = '97d8d93a-8973-42b2-b140-7133819be2db';
  String deviceSerial = 'L42332443';
  final _flutterYunmouPlugin = FlutterYunmou();

  bool isSound = true;

  @override
  void initState() {
    super.initState();
  }

  Future<void> init() async {
    final res1 = await _flutterYunmouPlugin.initSDK(authToken);
    final res2 = await _flutterYunmouPlugin.initPlayer(deviceSerial, 1);
  }

  // 开始播放
  Future<void> start() async {
    final res = await _flutterYunmouPlugin.startRealPlay();
  }

  // 停止播放
  Future<void> stop() async {
    final res = await _flutterYunmouPlugin.stopRealPlay();
  }

  // 开启/关闭声音
  Future<void> changeSound() async {
    bool res = false;
    if (isSound) {
      res = await _flutterYunmouPlugin.closeSound();
    } else {
      res = await _flutterYunmouPlugin.openSound();
    }
    setState(() {
      if (res) {
        isSound = !isSound;
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          children: [
            Wrap(
              spacing: 8.0, // 子元素之间的水平间距
              runSpacing: 8.0, // 子元素之间的垂直间距
              children: [
                ElevatedButton(
                  onPressed: () async {
                    print('开始初始化');
                    await init();
                    print('结束初始化');
                  },
                  child: const Text('初始化SDK'),
                ),
                ElevatedButton(
                  onPressed: () async {
                    await start();
                  },
                  child: const Text('开始播放'),
                ),
                ElevatedButton(
                  onPressed: () async {
                    await stop();
                  },
                  child: const Text('停止播放'),
                ),
                ElevatedButton(
                  onPressed: () async {
                    await changeSound();
                  },
                  child: Text('${isSound ? "关闭声音" : "开启声音"}'),
                ),
              ],
            ),
            const SizedBox(height: 20),
            Container(
              height: 200,
              decoration: const BoxDecoration(color: Colors.blue),
              child: const YunmouVideoView(),
            ),
          ],
        ),
      ),
    );
  }
}
```


#### 其它问题

海康云眸SDK版本：HikCloudOpenSDK_v1.3.1_20230913.aar

gradle版本: 7.3.0
