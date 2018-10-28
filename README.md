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
- Checkout your shopping cart and send your shopping items to be processed.

## Libraries used in this project
* [Picasso](http://square.github.io/picasso/)
* [Junit](https://developer.android.com/training/testing/unit-testing/local-unit-tests.html)
* [RecyclerView](https://developer.android.com/reference/android/support/v7/widget/RecyclerView)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [Retrofit](https://square.github.io/retrofit/)
* [Butterknife](http://jakewharton.github.io/butterknife/)


## High-level description of the packages
It has been decided to use the MVP architecture as it enables to mock classes with ease thus making it testable. But also, as it separates the different parts of the application, the maintenance of the software gets much easier.

It has been decided as well to use feature separation by packages, as opposed to package by layer approach, as again, it improves readability, separation of concerns and modularization, as parts can be changed without compromising one another.

- Packages:

* Adapter - This package holds the adapter classes used for each one of its respective recyclerviews.

* Utils - This package contains classes that provide some sort of helper functionality to the different packages of the project.

* Model - This package holds the classes that represent the data that is used internally in the app. For instance, the representation of a product and a cart is defined in there.

* Remote - It contains the interface class that define the endpoints used by the API to pull data from the server, and also a Network class that handles the set up of the connection.

* Main - This package has the Activity that handles the communication amongst the Fragments that use it as base layout for the different screens. It also has a Presenter for handling the Cart datastructure (which will be revised in the next stages).

* ProductFragment - This package contains the view/fragment of the main screen of the app. It displays the products and menus available to select the clothes' categories and so on. It also has the presenter that handles the communication between fragment/view and the repository class, passing data from the repository through the presenter to the fragment by using the view and repository interface.

* ProductDetailsActivity - It holds the classes that are responsible for displaying a product individually on the screen and passing the action choosen by the user to either stores the data in the SQLite database (when product is favourited) or in memory data-structure (HashTable for the cart). 

* FavouritesFragment - It has the responsability to pull the favourited products from the database and display it on the Favourite fragment.

* CartFragment - It has the responsability to retrieve the products that are in the cart and send them to a server so that the cart can be processed there.

* Data - It handles the database operations and definition. It makes use of Content providers for better communicaiton and isolation between those components.


## Features/Issues to be implemented or fixed

* Possibly store the Cart in the SQLite database as opposed to keeping it in a in-memory data-strucutre.
* Fix some issues with the cart data-structure being destroyed every time the activity is resumed.
* Fix problem with onSaveInstanceBundle and onRestoreBundle not working properly.
* Improve the whole UI by getting better pictures, make the layout and buttons more attractive on the Cart screen.
* Use DELETE request to remove products from the basket.
* Send product straight to the server as it gets added to the basket.
* Do some refactoring on the whole app, implementing unit-tests for the different feature packages.
* Possibly implement Espresso and Robolectric tests for testing the screens and actions across the screens.


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
