# Redux-MVI-for-Android

[![Release](https://jitpack.io/v/JekaK/Redux-MVI-for-Android.svg)](https://jitpack.io/#JekaK/Redux-MVI-for-Android) [![codebeat badge](https://codebeat.co/badges/aac325a3-65b2-45c5-822f-db067b098434)](https://codebeat.co/projects/github-com-jekak-redux-mvi-for-android-main)

This repository provides a Redux-MVI architecture implementation for Android applications. It is designed to help developers build scalable, maintainable, and testable Android applications.

## Installation

To install the library in your Android project, follow these steps:

1. Add the JitPack repository to your build file. To do this, add the following lines to your `build.gradle` file:

   ```gradle
   allprojects {
       repositories {
           maven { url 'https://jitpack.io' }
       }
   }
2. Add the dependency to your app's build.gradle file:

   ```gradle
   dependencies {
        implementation 'com.github.JekaK:Redux-MVI-for-Android:version'
   }

Note that you should replace `version` with the [latest release](https://github.com/JekaK/Redux-MVI-for-Android/releases) version available on JitPack.

## Example

https://github.com/JekaK/Redux-MVI-for-Android/blob/b60d470e5ce9dfb3f6ed48a124dcd16fedc9fc79/sample/src/main/java/com/krykun/sample/action/AddCounterAction.kt#L8-L14

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

