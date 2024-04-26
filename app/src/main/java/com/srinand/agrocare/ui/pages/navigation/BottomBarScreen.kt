package com.srinand.agrocare.ui.pages.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route : String,
    val title: String,
    val selectedIcon : ImageVector,
    val unSelectedIcon : ImageVector,
    val hasNotifications : Boolean
) {
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home,
        hasNotifications = false
    )
    object Notifications : BottomBarScreen(
        route = "notifications",
        title = "Notifications",
        selectedIcon = Icons.Filled.Notifications,
        unSelectedIcon = Icons.Outlined.Notifications,
        hasNotifications = true
    )
    object Logout : BottomBarScreen(
        route = "logout",
        title = "Logout",
        selectedIcon = Icons.Filled.Logout,
        unSelectedIcon = Icons.Outlined.Logout,
        hasNotifications = false
    )
}