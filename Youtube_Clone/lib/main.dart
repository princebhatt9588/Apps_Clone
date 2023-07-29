import 'package:flutter/material.dart';
import 'package:flutter_youtube_clone_app/screens/home_page.dart';

void main() {
  runApp(YoutubeCloneApp());
}

class YoutubeCloneApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter YouTube Clone',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: HomePageScreen(),
    );
  }
}
