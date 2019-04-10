# Behavioural-Quiz

Introduction

This project is an Android Application Project. I have implemented Personality test android application. There are number of questions based on category selected by user along with the options. The questions are displayed as view pager and options selected by the user are saved in the database. There is floating action button displayed in the First screen to retrieved the result from database.

The Json response is stored in assets folder. Json response can be retrieved from server API using retrofit library.

Architecture Flow

Approach

Following key points have been considered –
•	Architecture – MVVM
•	Android Architecture Component – ViewModel, LiveData, Room
•	Networking – Retrofit2
•	Material Design

MVVM Architecture -  

The project is logically divided into three modules.  Model, View and ViewModel.  

Model - The classes in the “repository” package represents the model and its helper classes to retrieve the model.  Here data is the list of questions and options based on the category retrieved from the Server APIs.

View – All the classes in the “ui” package.  These are basically the Activities– SelectCategoryActivity, QuestionListActivity & SavedPersonalityDataActivity, Fragments  - QuestionListFragment and Adapters – SelectCategoryAdapter, QuestionPageAdapter and SavedPersonalityDataAdpater.  

These classes are meant for showing the Category, Questions List based on the category and also showing the options saved by the user in  UI. Personality questions are displayed through the View pagers and FragmentStatePagerAdpater. User has to keep swiping after answering each question. To check the options provided\saved by user, there is floating action button given in the SelectCategory screen which retrieves the answers\options from database.

ViewModel - The classes in the “viewmodel” package are those classes which deals with the logic of retrieving the data and handing over to the registered view.  It encapsulates the details of how to retrieve the data and helps the view to focus on showing the data.

With MVVM the code is structured, modularised and readable.


Environment – 

•	Android Studio 3.3.2

Libraries – 

Couple of well adapted libraries has been used in this project.  
•	Retrofit2 – ver 2.3.0
•	Android support – 27.1.1

TODOs

•	Testing – include as many tests as possible – UI, UT etc.
•	More Error handling.
•	Dependency Injection (Dagger).

Thank you for your time.

Shilpa Kathal
