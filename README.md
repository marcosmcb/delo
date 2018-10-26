# Delo App

[![Crates.io](https://img.shields.io/crates/l/rustc-serialize.svg?maxAge=2592000)]()

App created for testing MVP architecture and TDD development.

This app has been developed using the MVP (Model - View - Presenter) architectural pattern. 
With MVP you separate the business and persistence logic out of the Activity and Fragment.
It also makes testing much easier, once you can mock your classes by using dependency injection and also separate Android code from Java code.
Making it much easier to test your app once you can isolate tests that require the JVM or the Android platform.

The app makes use of different features of Android. Such as - Adaptative UI, Use of Loaders, RecyclerView, 
Content Providers, RxAndroid and much more.

## Main Features

- Display products available in a Clothes Store from Apiary [Open API](https://ddshop.docs.apiary.io/).
- You can see all the products available on the main screen and also filter them by category
- You might also create a wishlist with your favourites products and have those available even when you are offline.
- Checkout your shopping cart is yet not implemented, but it will be available soon.

## Libraries used in this project
* [Picasso](http://square.github.io/picasso/)
* [Junit](https://developer.android.com/training/testing/unit-testing/local-unit-tests.html)
* [RecyclerView](https://developer.android.com/reference/android/support/v7/widget/RecyclerView)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [Retrofit](https://square.github.io/retrofit/)
* [Butterknife](http://jakewharton.github.io/butterknife/)


## Working with the source code
Just download the code and compile it with Android Studio


## License

Copyright 2018 Marcos Cavalcante

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
