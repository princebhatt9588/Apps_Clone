import 'package:flutter/material.dart';
import 'package:flutter_youtube_clone_app/common/colors.dart';
import 'package:flutter_youtube_clone_app/json/home_video.dart';
import 'package:video_player/video_player.dart';

class VideoDetailScreen extends StatefulWidget {
  // Widget properties here...

  @override
  _VideoDetailPageState createState() => _VideoDetailPageState();
}

class _VideoDetailPageState extends State<VideoDetailScreen> {
  bool isSwitched = true;
  // Video player related variables...
  // ...

  @override
  void initState() {
    super.initState();
    // Initialize video player...
    // ...
  }

  @override
  void dispose() {
    super.dispose();
    _controller.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color(0xFF1b1c1e),
      body: getBody(),
    );
  }

  Widget getBody() {
    var size = MediaQuery.of(context).size;
    return SafeArea(
      child: Column(
        children: <Widget>[
          // Video player widget and controls...
          // ...

          // Video details and options...
          // ...
        ],
      ),
    );
  }

  Future<void> _clearPrevious() async {
    await _controller?.pause();
    return true;
  }

  Future<void> _startPlay(videoItem) async {
    setState(() {
      _initializeVideoPlayerFuture = null;
    });
    Future.delayed(const Duration(milliseconds: 200), () {
      _clearPrevious().then((_) {
        _initializePlay(videoItem['video_url']);
      });
    });

    // Update video details with new state...
    // ...
  }

  Future<void> _initializePlay(String videoPath) async {
    _controller = VideoPlayerController.asset(videoPath);
    _controller.addListener(() {
      setState(() {
        _playBackTime = _controller.value.position.inSeconds;
      });
    });
    _initializeVideoPlayerFuture = _controller.initialize().then((_) {
      _controller.seekTo(newCurrentPosition);
      _controller.play();
    });
  }
}
