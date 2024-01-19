
import 'flutter_yunmou_platform_interface.dart';
import 'package:flutter/services.dart';

export 'yunmou_video_view.dart';

class FlutterYunmou {
  static const _channel = MethodChannel("flutter_yunmou");

  static const _pluginChannel = MethodChannel("flutter_yunmou/plugin");

  // Future<String?> getPlatformVersion() {
  //   return FlutterYunmouPlatform.instance.getPlatformVersion();
  // }

  // sdk 初始化
  Future<bool> initSDK(String authToken) async{
    final res = await _pluginChannel.invokeMethod('initSDK', {
      'authToken': authToken,
    });
    return res;
  }

  // 初始化播放器
  Future<bool> initPlayer(String deviceSerial, int channelNo) async {
    final res = await _channel.invokeMethod('initPlayer', {
      'deviceSerial': deviceSerial,
      'channelNo': channelNo,
    });
    return res;
  }

  // 释放
  Future<bool> videoRelease() async {
      final res = await _channel.invokeMethod('release');
      return res;
  }

  // 开启声音
  Future<bool> openSound() async {
      final res = await _channel.invokeMethod('sound', {
        'open': true,
      });
      return res;
  }

  // 关闭声音
  Future<bool> closeSound() async {
    final res = await _channel.invokeMethod('sound', {
      'open': false,
    });
    return res;
  }

  // 开始预览播放
  Future<bool> startRealPlay() async {
      final res = await _channel.invokeMethod('startRealPlay');
      return res;
  }

  // 停止预览播放
  Future<bool> stopRealPlay() async {
    final res = await _channel.invokeMethod('stopRealPlay');
    return res;
  }
}
