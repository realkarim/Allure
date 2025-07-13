package com.realkarim.navigator.destination

const val SIGNUP_ROUTE = "SignUpRoute"

class SignUpDestination : NavigationDestination {
  override fun toRoute(): String = Screens.SignUpScreenRoute.route
}
