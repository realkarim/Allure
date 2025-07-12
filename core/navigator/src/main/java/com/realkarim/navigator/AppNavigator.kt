package com.realkarim.navigator

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.Flow

interface AppNavigator {
    // Navigate to the previous a parent screen
    fun navigateUp(): Boolean

    // Navigate to the previous screen in the back stack
    fun popBackStack()


    fun navigate(
        destination: String,
        builder: NavOptionsBuilder.() -> Unit = { launchSingleTop = true }
    ): Boolean

    val destinations: Flow<NavigatorEvent>
}