package com.srinand.agrocare.ui.pages.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.srinand.agrocare.ui.pages.HomePage
import com.srinand.agrocare.ui.pages.MainPage
import com.srinand.agrocare.ui.pages.LoginPage
import com.srinand.agrocare.ui.pages.NotificationsPage
import com.srinand.agrocare.ui.pages.StartUpPage
import com.srinand.agrocare.viewmodels.RoomViewModel


val LocalNavigator = compositionLocalOf <NavHostController>{
    error("Helloooo")
}
@Composable
fun AppNavHost(
    navHostController: NavHostController = rememberNavController(),
    startDestination: String,
) {
    val viewModel = hiltViewModel<RoomViewModel>()
    val state by viewModel.state.collectAsState()
    CompositionLocalProvider(
        LocalNavigator provides navHostController
    ) {
        NavHost(navController = navHostController, startDestination = startDestination){
            composable(Routes.StartUpPage.route){
                StartUpPage()
            }
            composable(Routes.LoginPage.route){
                LoginPage()
            }
            composable(Routes.MainPage.route){
                MainPage()
            }
        }
    }
}