import 'package:flutter/material.dart';
import 'dart:async';

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
  Future<void> changeSound()  async {
    bool res =  false;
    if (isSound) {
      res = await _flutterYunmouPlugin.closeSound();
    }
    else {
      res = await _flutterYunmouPlugin.openSound();
    }
    setState(()  {
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
                  onPressed: () async{
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
