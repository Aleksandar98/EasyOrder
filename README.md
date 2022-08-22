# EasyOrder

# Libraries and instruments

- Dependency injections - `Dagger Hilt`
- Network - `Retrofit2`
- Concurrency, app layers communication - `RxJava3`
- Navigation - `AndroidX Navigation`
- Local Persistance - `Data Store`
- Image Loading - `Glide`
- Json manipulation - `Gson`
- Dialogs - `Sweet Alert`

### Testing
- Mocking - `Mockito`
- Assertion - `Google Truth`
- UI tests - `Esspresso`

# Architecture

I used clean architecture approach with three layers: Data, Domain, UI (Presentation). In the UI layer, I employ the
MVVM pattern: Model-View-ViewModel.

![alt text](images/android_app_architecture.png)

## Clean Architecture
The domain layer the center of the app. It contains business logic and should reflect the real world.
While the way the data is presented to the user (UI layer) or the way the data is retrieved from the server or stored in a DB (Data layer) may change, the domain logic and models should stay fixed, for the most part.
The classes in the domain have no dependencies on the other layers.

## Data
- ApiModels live in the Data layer. They match the server models 1-1 and are the output of the MenuPlaygroundApi.
- Providers offer RxJava3 interfaces.

## Domain
- The Domain Models (simply called Models) live in the Domain. They use real Classes as fields and model the real world.
- The Contract interfaces in the Domain describe the data that the Domain needs from the Data layer.
- UseCases live in the Domain. They contain the business logic of the App. They are responsible for getting Model data from the Data Layer through DataContracts, making decisions about it and delivering results down to the UI Layer.
- UseCases offer RxJava3 interfaces.

## UI - MVVM
- We use Android Architecture Components on this layer: Android ViewModels and LiveData.
- Android ViewModels talk directly to the UseCases in the Domain layer. They get Domain models, transform them with the help of the ViewDataMappers to ViewData and make decisions about how to present the data in the Views.
- Views are represented by Activities, which talk to their ViewModels and get their ViewData through LiveData observables.


# SplashActivity
* Splash screen is responsible for checking weather we have token and user name saved from last login if we have token saved we are skipping login process.

# MainActivity
* Main Activity is responsible for navigation between fragments
* Main Activity is responsible for handling internet connection status


# LoginFragment
* Login Fragmet is responsible for loging in the user and after successful login saving login token and user name to local storage (DataStore) 
* Login Fragmet is responsible for validating Email format

# Listfragment
* List Fragment is responsible for showing all restaurants and allowing user to log out. Clicking on restaurant item takes tou to DeatailFragment

# DeatailFragment
* Deatail Fragment is responsible for showing deatail about particular restaurant and allowing user to log out.

# Testing

### Unit tests
* Email Validator regex is tested by asserting returned value for few email examples 
>test@testmenu.app -> true
>
>jasdoasd123@asdjap.com -> true
>
>jasdoasd123asdjap.com -> false
>
* SplashActivityViewModel is tested by mocking response from `GetTokenUseCase` and `GetUserNameUseCase` and asserting returned value
* LoginViewModel is tested by Mocking LoginUseCase and providing mocked response and testing ViewModels behavior

`Mock response 200 OK -> ViewModel returns Loading -> Success`

`Mock response 400 NOT OK -> ViewModel returns Loading -> Error`

### UI tests

UI testing is done by going through steps:
1. Open the app
1. Sign in
1. Wait for Restaurants to load
1. Open first Restaurant 
1. Logut
1. Sign in
1. Wait for Restaurants to load
1. Close App
1. Open the App
1. Make sure login is skiped
