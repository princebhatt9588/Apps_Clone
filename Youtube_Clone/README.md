# Flutter YouTube Clone App

The **Flutter_youtube_clone_app**  is a project developed in Flutter that replicates the functionality of the YouTube app. It serves as a YouTube clone, showcasing the power and versatility of Flutter for building mobile applications with a similar look and feel to the original YouTube.


![Screen_Shot_2019_11_06_at_5 04 08_PM](https://github.com/princebhatt9588/Apps_Clone/assets/117750531/5c04865e-4201-42c6-bad5-6d9c0afe87ef)



## Project Details

- The entry point for the app is defined in `main.dart`.
- The app has been designed with Flutter to create a YouTube-like user interface and experience.
- It aims to provide a clone of key features found in the original YouTube app.

## Release Steps for Android

To generate an Android release of the app, follow these steps:

1. Ensure you have Flutter SDK installed.
2. Get the required packages using `flutter packages get`.
3. Open the `runner.xcworkspace` file from the "ios" folder.
4. Update the version code in the YAML file.
5. To generate a FAT APK, run `flutter clean` and then `flutter build apk --release`.
6. To generate split APKs, run `flutter clean` and then `flutter build apk --split-per-abi --release`.

## Release Steps for iOS

To generate an iOS release of the app, follow these steps:

1. Set the iOS deployment target.
2. Setup Flutter SDK and get the required packages.
3. Open `runner.xcworkspace` from the "ios" folder.
4. Update the version code in the YAML file.
5. To generate the `runner.app` file, run `flutter clean` and then `flutter build ios --release`.
6. Open Xcode, run/build the project to check for any errors.
7. Select Product -> Archive and follow the steps for uploading.
