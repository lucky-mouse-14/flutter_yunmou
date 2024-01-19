import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_yunmou/flutter_yunmou.dart';
import 'package:flutter_yunmou/flutter_yunmou_platform_interface.dart';
import 'package:flutter_yunmou/flutter_yunmou_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterYunmouPlatform
    with MockPlatformInterfaceMixin
    implements FlutterYunmouPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final FlutterYunmouPlatform initialPlatform = FlutterYunmouPlatform.instance;

  test('$MethodChannelFlutterYunmou is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterYunmou>());
  });

  test('getPlatformVersion', () async {
    FlutterYunmou flutterYunmouPlugin = FlutterYunmou();
    MockFlutterYunmouPlatform fakePlatform = MockFlutterYunmouPlatform();
    FlutterYunmouPlatform.instance = fakePlatform;

    expect(await flutterYunmouPlugin.getPlatformVersion(), '42');
  });
}
