# Flutter YouTube Clone App

The **flutter_youtube_clone_app** project centers around the "main.dart" file as its entry point. It is designed to serve as a YouTube clone using Flutter.

To generate an Android release, follow these steps:

1. Generate FAT APK:
   - Run `flutter clean`.
   - Build the APK with `flutter build apk --release`.

2. Generate Split APK's:
   - Run `flutter clean`.
   - Build the APKs per ABI with `flutter build apk --split-per-abi --release`.

For iOS release, follow these steps:

1. Set iOS deployment target.

2. Set up Flutter SDK and get packages.

3. Open `runner.xcworkspace` from the 'ios' folder.

4. Update version code in the YAML file.

To generate the `runner.app` file:

1. Run `flutter clean`.
2. Build iOS release with `flutter build ios --release`.
