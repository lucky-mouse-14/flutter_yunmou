import 'dart:io';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class YunmouVideoView extends StatelessWidget {
  const YunmouVideoView({Key? key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    if (Platform.isAndroid) {
      return const AndroidView(
        viewType: 'flutter_yunmou/videoView',
        creationParamsCodec: StandardMessageCodec(),
      );
    }

    throw UnimplementedError();
  }
}