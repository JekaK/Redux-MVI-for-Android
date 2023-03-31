# Redux-MVI-for-Android

[![Release](https://jitpack.io/v/JekaK/Redux-MVI-for-Android.svg)](https://jitpack.io/#JekaK/Redux-MVI-for-Android) [![codebeat badge](https://codebeat.co/badges/aac325a3-65b2-45c5-822f-db067b098434)](https://codebeat.co/projects/github-com-jekak-redux-mvi-for-android-main) [![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

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
1. Start a Koin DI(it's powered by Koin, so you don't have a lot of choise what DI use). viewModelModule is module provided by sample. You will create it by yourself. Or not if you not using ViewModels at your project. Other modules provided by library and SHOULD BE CONNECTED AS BELOW:

```kotlin
 class App : Application() { 
  
     override fun onCreate() { 
         super.onCreate() 
         setupDIGraph() 
     } 
  
     private fun setupDIGraph() { 
         startKoin { 
             androidContext(this@App) 
             modules( *listOfModules, *viewModelModule) 
         } 
     } 
 } 
 ```

2. Then I recomend you to create a package called "Presentation" and put 3 files there: State, Props and ViewModel.

State class is for saving state of app. Props is mapped class as in Clean Arch and it created for state that will be used only in views and not the whole one project state. ViewModel is for communicating between view and state updates.

State is DataClass:

```kotlin
data class MainState( 
     val isOpen: Boolean = false, 
     val counter: Int = 0 
 ) 
 ```

Props also a DataClass. And in my case it contains a mapper to(cause it's tiny and not deserve a separate class :D):

```kotlin
 data class MainProps( 
     val counter: Int = 0, 
     val addCounterAction: () -> Unit = {} 
 ) 
  
 fun MainState.toProps(addCounterAction: () -> Unit): MainProps { 
     return MainProps(this.counter) { 
         addCounterAction() 
     } 
 } 
 ```
ViewModel contains a store from Redux architecture for dispatching actions and have a link to state for view usage:

```kotlin
 class MainViewModel( 
     private val store: Store<Action, AppState>, 
     private val bindingDispatcher: CoroutineDispatcher 
 ) : ViewModel() { 
 ```
In ```init block``` I adding a state for this screen to state list by pass it to dispatched action. This give us ability to save state and retrieve it from Flow.

```kotlin
 init { 
     store.dispatch(AddStateAction(MainState())) 
 } 
 ```

Also ```mainProps``` give us a Flow of props that contains state mapped to props and can be used in our Activity or Fragment or Composable function navigated by Composable navigation.

```kotlin
 fun mainProps() = store.stateFlow() 
     .getStateUpdatesMapped<MainState, MainProps>(bindingDispatcher) { 
         it.toProps { 
             store.dispatch(AddCounterAction()) 
         } 
     } 
```

3. Create an action for some busines logic. In our case we have a button with counter. When we click on button action is dispatching and redusing a new state. This will trigger a state update via flow and show result to user:

```kotlin
class AddCounterAction : ReducibleAction { 
     override fun reduce(state: AppState): AppState { 
         return state.applyForState<MainState> { 
             it.copy(counter = it.counter + 1) 
         } 
     } 
 } 
 ```
4. Then use it in Acivity as ```collectAsState``` function:

```kotlin
 val props = viewModel.mainProps().collectAsState(initial = MainProps()) 
 Column( 
     modifier = Modifier.fillMaxSize(), 
     horizontalAlignment = Alignment.CenterHorizontally, 
     verticalArrangement = Arrangement.Center 
 ) { 
     CounterView(props.value.counter) 
     Spacer(modifier = Modifier.height(20.dp)) 
     AddButton { 
         props.value.addCounterAction() 
     } 
 } 
 ```
 
## Middleware

The Redux-MVI-for-Android package provides middleware as a way to intercept and modify actions and state changes in the Redux store. One example of middleware provided in this package is the "MainMiddleware", which send a message action via navigation requests when it intercepted by it.

```kotlin

class MainMiddleware : Middleware<Action, Store<Action, AppState>> {

    /**
     * > When the `AddCounterAction` is dispatched, dispatch a `CounterToastAction`
     *
     * The `dispatchCounterToastAction` function is defined as follows:
     */
    override fun dispatch(
        action: Action,
        store: Store<Action, AppState>,
        next: Dispatcher<Action, Store<Action, AppState>>,
    ): Action {
        // You can make a dispatch right in return, but if you want not updated state and somehow modify it before
        // action is will be dispatched to next middleware - make it as in example below
        val next = next.dispatch(action, store)

        when (action) {
            is AddCounterAction -> dispatchCounterToastAction(store)
        }

        return next
    }


    /**
     * "Dispatch a toast action that shows the current counter value."
     *
     * The function is private because it's only used internally by the MainActivity
     */
    private fun dispatchCounterToastAction(store: Store<Action, AppState>) {
        val counter = store.getState().findState<MainState>().counter

        store.dispatch(ShowCounterToastAction(counter))
    }

}
```
For middleware setup you should provide your own ```MiddlewareModule``` as in Sample package and connect it to Koin in App class.

**!!!IPORTANT!!!**

For better performance you can create a custom feature for setup only this middlewares that you will use in particular screen.

Example: you have a project with 100+ middlewares that potentially can handle all actions. They all will be added to a chain of middlewares. But in different screens you need only few middlewares in one, so you can add a Feature module and setup a middleware as Feature fo some screen by emiiting ```(SetupFeature(Feature)``` in ```init``` block of ViewModel and ```CleanupFeature(Feature)``` in ```onCleared()``` function of ViewModel. For additional info check ```MainViewModel``` at Presentation package, ```FeatureModle``` and ```MiddlewareModule``` at DI package. All in sample package as well.
 
## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

