package com.realkarim.allure.nav

import LoginScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.realkarim.home.HomeScreen
import com.realkarim.navigator.AppNavigator
import com.realkarim.navigator.destination.HomeDestination
import com.realkarim.navigator.destination.LoginDestination
import com.realkarim.navigator.destination.NavigationDestination
import com.realkarim.navigator.destination.SignUpDestination
import com.realkarim.signup.SignupScreen

private val composableDestinations: Map<NavigationDestination, @Composable (AppNavigator, NavHostController) -> Unit> =
  mapOf(
    SignUpDestination() to { _, _ -> SignupScreen() },
    HomeDestination to { _, navHostController -> HomeScreen(navHostController) },
    LoginDestination() to { appNavigator, _ -> LoginScreen(appNavigator = appNavigator) },
  )

fun NavGraphBuilder.addComposableDestinations(
  appNavigator: AppNavigator,
  navHostController: NavHostController,
) {
  composableDestinations.forEach { entry ->
    val destination = entry.key
    composable(
      route = destination.toRoute(),
      arguments = destination.arguments,
      deepLinks = destination.deepLinks,
    ) {
      entry.value(appNavigator, navHostController)
    }
  }
}
