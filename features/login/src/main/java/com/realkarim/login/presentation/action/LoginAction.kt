package com.realkarim.login.presentation.action

import com.realkarim.domain.model.ErrorMessage
import com.realkarim.domain.model.User
import com.realkarim.login.presentation.error.LoginError

sealed class LoginInput {
  data class UserNameUpdated(val username: String) : LoginInput()
  data class PasswordUpdated(val password: String) : LoginInput()
  data object LoginButtonClicked : LoginInput()
  data object RegisterButtonClicked : LoginInput()
}

sealed class LoginOutput {
  data class NavigateToMain(val user: User) : LoginOutput()
  data object NavigateToRegister : LoginOutput()
  data class ShowError(val errorMessage: ErrorMessage) : LoginOutput()
}

data class LoginUiState(
  val userName: String = "",
  val password: String = "",
  val isLoginButtonEnabled: Boolean = false,
  val userNameError: LoginError = LoginError.NoEntry,
  val passwordError: LoginError = LoginError.NoEntry,
) {
  fun showPasswordError() =
    passwordError != LoginError.NoError && passwordError != LoginError.NoEntry

  fun showUsernameError() =
    userNameError != LoginError.NoError && userNameError != LoginError.NoEntry
}
