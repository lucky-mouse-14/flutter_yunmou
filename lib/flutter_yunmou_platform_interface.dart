import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_yunmou_method_channel.dart';

abstract class FlutterYunmouPlatform extends PlatformInterface {
  /// Constructs a FlutterYunmouPlatform.
  FlutterYunmouPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterYunmouPlatform _instance = MethodChannelFlutterYunmou();

  /// The default instance of [FlutterYunmouPlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterYunmou].
  static FlutterYunmouPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterYunmouPlatform] when
  /// they register themselves.
  static set instance(FlutterYunmouPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  // Future<bool?> initSDK() {
  //   throw UnimplementedError('initSDK() has not been implemented;');
  // }
}
