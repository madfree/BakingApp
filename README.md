# Baking App

This is an Android Baking App that shows a collection of recipes, their ingredients and video-guided steps of the preparation. The app uses the model-view-viewmodel (mvvm) architecture pattern and Android architecture components to implement this pattern in the app. Other main features of the app are: user interface with master-detail flow, media player integration and a homescreen widget.
The app has been build as part of the Udacity Android Developer Nanodegree.

## Features

- Android Architecture Components
    - Room, as a database, to avoid unnecessary boilerplate and to easily convert SQLite table data to Java objects
    - ViewModel to store UI-related data and keeping in on app rotation
    - LiveData to make use of data objects that notify views, when underlying data is changing
- Master/Detail Flow for a clean and intuitive user interface on phone and tablet
- Retrofit is used for easier networking, asynchronous data loading, exception handling and JSON parsing
- A media player (Exoplayer) is used to load and display videos
- The app is accompanied by a homescreen widget

## Showcase

<img src=/screenshots/1.png width="200">   <img src=/screenshots/2.png width="200">   <img src=/screenshots/4.png width="200">

<img src=/screenshots/3.png width="500">

## Getting Started / Installation
1. Download the zip-file with the code and import it into Android Studio OR clone the code with Android Studio.
2. Start the app via emulator

## License
This app is Copyright © 2020 madfree. It is free software, and may be redistributed under the terms specified in the [LICENSE](/LICENSE) file.
