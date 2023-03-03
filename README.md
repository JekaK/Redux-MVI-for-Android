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
1. Start a Koin DI(it's povered by Koin, so you don't have a lot of choise what DI use). viewModelModule is module provided by sample. You will create it by yourself. or not is you not use ViewModels at your project. Other modules provided by library

https://github.com/JekaK/Redux-MVI-for-Android/blob/9be3b88fed3b98752fe8cbfda7f53b84db5aaf9a/sample/src/main/java/com/krykun/sample/App.kt#L11-L24

2. Then I recomend you to create a package called "Presentation" and put 3 files there: State, Props and ViewModel.

State class is for saving state of app. Props is mapped class as in Clean Arch and it created for state that will be used only in views and not the whole one project state. ViewModel is for communicating between view and state updates.

State is DataClass:
https://github.com/JekaK/Redux-MVI-for-Android/blob/9be3b88fed3b98752fe8cbfda7f53b84db5aaf9a/sample/src/main/java/com/krykun/sample/presentation/MainState.kt#L3-L6

Props also a DataClass. And in my case it contains a mapper to(cause it's tiny and not deserve a separate class :D):

https://github.com/JekaK/Redux-MVI-for-Android/blob/9be3b88fed3b98752fe8cbfda7f53b84db5aaf9a/sample/src/main/java/com/krykun/sample/presentation/MainProps.kt#L3-L12

ViewModel contains a store from Redux architecture for dispatching actions and have a link to state for view usage:

https://github.com/JekaK/Redux-MVI-for-Android/blob/79ad58f62dc4dff219b7ec520157bff97cac9a01/sample/src/main/java/com/krykun/sample/presentation/MainViewModel.kt#L12-L15

In ```init block``` I adding a state for this screen to state list by pass it to dispatched action. This give us ability to save state and retrieve it from Flow.

https://github.com/JekaK/Redux-MVI-for-Android/blob/79ad58f62dc4dff219b7ec520157bff97cac9a01/sample/src/main/java/com/krykun/sample/presentation/MainViewModel.kt#L17-L19

Also ```mainProps``` give us a Flow of props that contains state mapped to props and can be used in our Activity or Fragment or Composable function navigated by Composable navigation.

https://github.com/JekaK/Redux-MVI-for-Android/blob/79ad58f62dc4dff219b7ec520157bff97cac9a01/sample/src/main/java/com/krykun/sample/presentation/MainViewModel.kt#L21-L26

3. Create an action for some busines logic. In our case we have a button with counter. When we click on button action is dispatching and redusing a new state. This will trigger a state update via flow and show result to user:

https://github.com/JekaK/Redux-MVI-for-Android/blob/9be3b88fed3b98752fe8cbfda7f53b84db5aaf9a/sample/src/main/java/com/krykun/sample/action/AddCounterAction.kt#L8-L14

4. Then use it in Acivity as ```collectAsState``` function:

https://github.com/JekaK/Redux-MVI-for-Android/blob/9be3b88fed3b98752fe8cbfda7f53b84db5aaf9a/sample/src/main/java/com/krykun/sample/MainActivity.kt#L33-L44

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

