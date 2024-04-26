package com.srinand.agrocare.ui.pages.navigation

sealed class Routes(val route: String) {
    object StartUpPage : Routes("startup")
    object LoginPage : Routes("login")
    object MainPage : Routes("main")
    object HomePage : Routes("home")
    object DevicePage : Routes("devicePage")
    object NotificationsPage : Routes("notifications")
}