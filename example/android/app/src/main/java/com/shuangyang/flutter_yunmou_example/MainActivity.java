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
