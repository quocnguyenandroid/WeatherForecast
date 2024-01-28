# Weather App

A weather android app with Clean Architecture

## Instruction
* Select variant before build.
<img src="./screenshot/select_variant.png" height="240"/>

* Option 1: Connect your device and build.
<img src="./screenshot/build_1.png" height="48"/>

* Option 2: Create APK file and install on device.
<img src="./screenshot/build_2.png" height="480"/>

## Architecture
* Code is written with Clean Architecture.
<img src="./screenshot/architecture.png" height="480"/>

* **Data layer** contains model and api config to get data from remote.
* **Domain layer** contains Repository methods to communicate with **Data layer**.
* **Presentation layer** using **MVVM** contain views and **ViewModel**.

## Dependencies
* [Coil](https://coil-kt.github.io/coil/): An image loading library for Android backed by Kotlin Coroutines.
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android): For dependency injection.
* [Retrofit](https://github.com/square/retrofit): Handle API.
* [OkHttp](https://square.github.io/okhttp/): For logging interceptor.
* [Coroutines](https://developer.android.com/kotlin/coroutines): For asynchronous task.

## Screenshot

<img src="./screenshot/home.png" height="480"/> <img src="./screenshot/search.png" height="480"/>
