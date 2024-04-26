package com.srinand.agrocare.ui.pages.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.srinand.agrocare.ui.pages.HomePage
import com.srinand.agrocare.ui.pages.LogoutPage
import com.srinand.agrocare.ui.pages.NotificationsPage
import com.srinand.agrocare.viewmodels.RoomViewModel

val LocalBottomNavigator = compositionLocalOf <NavHostController>{
    error("Helloooo")
}

@Composable
fun BottomNavGraph(navController: NavHostController) {
    val viewModel = hiltViewModel<RoomViewModel>()
    val state by viewModel.state.collectAsState()
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ){
        composable(BottomBarScreen.Home.route){
            HomePage(state, viewModel::onEvent)
        }
        composable(BottomBarScreen.Notifications.route){
            NotificationsPage()
        }
        composable(BottomBarScreen.Logout.route){
            LogoutPage()
        }
    }
}