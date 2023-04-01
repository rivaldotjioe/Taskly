package com.rivaldo.taskly.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val destination: DestinationScreen,
    val icon: ImageVector,
    val iconSelected : ImageVector,
    val title: String = destination.getTitle()
)

val listNavigationItem = listOf(
    NavigationItem(
        destination = DestinationScreen.HOME,
        icon = Icons.Outlined.Home,
        iconSelected = Icons.Filled.Home,
    ),
)
