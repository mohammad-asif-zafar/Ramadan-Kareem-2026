package com.hathway.ramadankareem2026.ui.bookmarks.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.bookmarks.presentation.BookmarksScreen
import com.hathway.ramadankareem2026.ui.navigation.Routes

@Composable
fun BookmarksRoute(
    navController: NavController
) {
    BookmarksScreen(
        onBack = { navController.popBackStack() }, 
        onDuaClick = { duaId ->
            navController.navigate("dua_detail/$duaId")
        },
        onAllahNameClick = { allahNameId ->
            navController.navigate("allah_name_detail/$allahNameId")
        },
        onMosqueClick = { mosqueId ->
            // Navigate to nearby mosque screen when mosque bookmark is clicked
            navController.navigate(Routes.MOSQUES)
        }
    )
}
