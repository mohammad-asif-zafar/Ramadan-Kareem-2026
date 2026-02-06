package com.hathway.ramadankareem2026.ui.home.components

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun NotificationPermissionHandler(
    pendingAction: (() -> Unit)?,
    onActionConsumed: () -> Unit
) {
    if (pendingAction == null) return

    val context = LocalContext.current

    // Android < 13 → permission not required
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        LaunchedEffect(pendingAction) {
            pendingAction()
            onActionConsumed()
        }
        return
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            pendingAction()
        }
        onActionConsumed()
    }

    LaunchedEffect(pendingAction) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            pendingAction()
            onActionConsumed()
        } else {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
