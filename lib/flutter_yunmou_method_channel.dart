import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_yunmou_platform_interface.dart';

/// An implementation of [FlutterYunmouPlatform] that uses method channels.
class MethodChannelFlutterYunmou extends FlutterYunmouPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_yunmou');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  // @override
  // Future<bool?> initSDK() async {
  //   final initResult = await methodChannel.invokeMethod<bool>('initSDK');
  //   return initResult;
  // }
}
