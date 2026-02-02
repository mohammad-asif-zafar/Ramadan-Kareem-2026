package com.hathway.ramadankareem2026.ui.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hathway.ramadankareem2026.ui.navigation.Routes
import com.hathway.ramadankareem2026.ui.theme.RamadanGreen

sealed class BottomNavItem(
    val route: String, val title: String, val icon: ImageVector, val badgeCount: Int? = null
) {
    object Home :
        BottomNavItem(route = Routes.HOME, title = Routes.HOME, Icons.Outlined.Home, badgeCount = 1)

    object Quran : BottomNavItem(
        route = Routes.QURAN,
        title = Routes.QURAN,
        Icons.AutoMirrored.Outlined.MenuBook,
        badgeCount = 2
    )

    object Qibla : BottomNavItem(
        route = Routes.QIBLA, title = Routes.QIBLA, Icons.Outlined.Explore, badgeCount = 4
    )
}

@Composable
fun RamadanBottomBar(navController: NavController) {

    val items = listOf(
        BottomNavItem.Home, BottomNavItem.Quran, BottomNavItem.Qibla
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {

        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route, onClick = {
                    navController.navigate(item.route) {
                        popUpTo(route = Routes.HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }, icon = {
                    Icon(item.icon, contentDescription = null)
                }, label = {
                    Text(text = item.title)
                }, colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = RamadanGreen,
                    selectedTextColor = RamadanGreen,
                    indicatorColor = RamadanGreen.copy(alpha = 0.15f),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                ), alwaysShowLabel = false
            )
        }
    }
}
