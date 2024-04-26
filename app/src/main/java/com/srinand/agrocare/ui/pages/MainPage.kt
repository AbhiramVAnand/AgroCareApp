package com.srinand.agrocare.ui.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.srinand.agrocare.ui.pages.navigation.BottomBarScreen
import com.srinand.agrocare.ui.pages.navigation.BottomNavGraph
import com.srinand.agrocare.ui.pages.navigation.LocalNavigator
import com.srinand.agrocare.ui.pages.navigation.Routes
import com.srinand.agrocare.ui.theme.ActiveNavItem
import com.srinand.agrocare.ui.theme.FadedBorder
import com.srinand.agrocare.ui.theme.InactiveNavItem
import com.srinand.agrocare.ui.theme.Primary
import com.srinand.agrocare.ui.theme.PrimaryVariant
import com.srinand.agrocare.ui.theme.Secondary
import com.srinand.agrocare.viewmodels.MQTTClientViewModel
import com.srinand.agrocare.viewmodels.RoomViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
) {
    val navController = rememberNavController()
//    viewModel.SubscribeToAlert()
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ){
        val viewModel = hiltViewModel<MQTTClientViewModel>()
        val navHostController = LocalNavigator.current
        val isfirst = viewModel.getIsFirst()
        if (isfirst){
            navHostController.popBackStack(route = Routes.MainPage.route, inclusive = false)
            viewModel.setIsNotFirst()
        }
        BottomNavGraph(navController = navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val NavBarItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = ActiveNavItem,
        selectedTextColor = ActiveNavItem,
        unselectedIconColor = InactiveNavItem,
        unselectedTextColor = InactiveNavItem,
        disabledIconColor = InactiveNavItem,
        disabledTextColor = InactiveNavItem,
        indicatorColor = Primary
    )

    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Notifications,
        BottomBarScreen.Logout
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth(1F)
            .background(PrimaryVariant),
        contentColor = Secondary,
        containerColor = PrimaryVariant
    ) {
        screens.forEachIndexed{ index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index ,
                onClick = {
                    selectedItemIndex = index
                    if (selectedItemIndex==0){
                        navController.popBackStack(route = BottomBarScreen.Home.route, inclusive = true)
                        navController.navigate(item.route)
                    }else{
                        navController.navigate(item.route)
                    }
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.hasNotifications){
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if(index == selectedItemIndex){ item.selectedIcon} else item.unSelectedIcon,
                            contentDescription = item.title
                        )
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        color = FadedBorder,
                        style = MaterialTheme.typography.bodyMedium
                    )},
                colors = NavBarItemColors
            )
        }
    }
}