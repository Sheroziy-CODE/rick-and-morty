
## Rich-And-Morty


### The App Overview

"Rick and Morty Universe" is a mobile application that allows users to explore the thrilling universe of the hit television series "Rick and Morty". The app provides comprehensive information about the series' characters and episodes, enabling users to dive deep into the lore and storyline.

Features:

- Characters: List and details of all characters from the series.
- Episodes: List and details of all episodes across different seasons.
- Local Database: The app utilizes a local database to cache data for quick retrieval and to provide an offline usage capability.

### Built With

* Kotlin: The project is built entirely in Kotlin, including the use of Coroutines and Flow for asynchronous programming.
* Android Jetpack: A suite of libraries, tools, and guidance to help developers write high-quality apps easier. The project uses several Jetpack components including ViewModel, LiveData, Navigation, and Hilt for dependency injection.
* Retrofit: A type-safe HTTP client for Android and Java, used in this project for network requests.
* Moshi: A modern JSON library for Android and Java, used to parse JSON into Java objects seamlessly.
* Coil: An image loading library for Android backed by Kotlin Coroutines, used to load and cache images.
* Realm: An easy-to-use mobile database that runs directly inside phones, tablets, or wearables, used in this project for local data storage and retrieval.
* JUnit, MockK, Truth, and Coroutine Test: Used for unit testing, mocking, and assertion of the application's components.
* Dagger Hilt: A dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in the project.

## Structure

The application follows Clean Architecture with MVVM (Model-View-ViewModel) on the presentation layer. It is divided into four main packages:

1) Data: This package contains the data sources, both remote and local. It includes classes to interact with these data sources and retrieve data from the "Rick and Morty" API and the local database.
2) Domain: This package holds the business logic of the application. It contains use cases and entities used by the application.
3) Presentation: This package represents the UI of the application with its respective ViewModel, divided into two sub-packages for characters and episodes.
4) Shared: This package contains utility classes, application-wide configurations, and common components shared across the project.

The separation of these packages ensures a clear separation of concerns and enhances the readability, scalability, and maintainability of the codebase.

## Coding style conventions

Rules:

- Omit needless words
- Every word should convey what your variable/function/class is doing.
- All folder should be snake_case, example folders: models, style, floor_plan
- All files should be UpperCamelCase + the name of the folder (in most cases), example files: ApiModel, MainScreenActivity
- All Class names should follow UpperCamelCase rule. Example: FindTable.
- All Variables names should follow LowerCamel case with


## Contact

* Sheroziy - sheroziy.saidkhodjaev@code.berlin


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
