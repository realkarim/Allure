package com.realkarim.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realkarim.login.domain.usecase.LoginUseCase
import com.realkarim.login.presentation.action.LoginInput
import com.realkarim.login.presentation.action.LoginOutput
import com.realkarim.login.presentation.action.LoginUiState
import com.realkarim.login.presentation.error.LoginError
import com.realkarim.login.presentation.validator.LoginValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val loginUseCase: LoginUseCase,
) : ViewModel() {

  var loginUiState = LoginUiState()

  // Just trying with channel, usually we would use StateFlow or LiveData
  private val _viewOutput: Channel<LoginOutput> = Channel()
  val viewOutput = _viewOutput.receiveAsFlow()

  fun setInput(input: LoginInput) {
    when (input) {
      is LoginInput.LoginButtonClicked -> login()
      is LoginInput.PasswordUpdated -> updateState { copy(password = input.password) }
      is LoginInput.RegisterButtonClicked -> sendOutput { LoginOutput.NavigateToRegister }
      is LoginInput.UserNameUpdated -> updateState { copy(userName = input.username) }
    }
  }

  private fun updateState(updatedState: LoginUiState.() -> LoginUiState) {
    loginUiState = loginUiState.updatedState()
    validate()
  }

  private fun validate() {
    val userNameError: LoginError = LoginValidator.userNameError(loginUiState.userName)
    val passwordError: LoginError = LoginValidator.passwordError(loginUiState.password)
    val isLoginButtonEnabled: Boolean = LoginValidator.canDoLogin(userNameError, passwordError)

    loginUiState = loginUiState.copy(
      isLoginButtonEnabled = isLoginButtonEnabled,
      userNameError = userNameError,
      passwordError = passwordError,
    )
  }

  private fun sendOutput(action: () -> LoginOutput) {
    viewModelScope.launch {
      _viewOutput.send(action())
    }
  }

  fun login() {
    viewModelScope.launch {
      loginUseCase.execute(
        LoginUseCase.Input(
          username = loginUiState.userName,
          password = loginUiState.password,
        ),
        success = {
        },
        error = {},
      )
    }
  }
}
