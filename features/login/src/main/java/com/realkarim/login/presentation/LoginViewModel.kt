package com.realkarim.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realkarim.domain.model.User
import com.realkarim.login.domain.usecase.LoginUseCase
import com.realkarim.login.presentation.action.LoginInput
import com.realkarim.login.presentation.action.LoginOutput
import com.realkarim.login.presentation.action.LoginUiState
import com.realkarim.login.presentation.error.LoginError
import com.realkarim.login.presentation.validator.LoginValidator
import com.realkarim.presentation.StateRenderer
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val loginUseCase: LoginUseCase,
) : ViewModel() {

  private var loginUiState = LoginUiState()

  private val _stateRendererMutableState = MutableStateFlow<StateRenderer<LoginUiState, User>>(
    StateRenderer.ScreenContent(loginUiState),
  )
  val stateRendererFlow: StateFlow<StateRenderer<LoginUiState, User>> get() = _stateRendererMutableState

  // Just trying with channel, usually we would use StateFlow or LiveData
  private val _viewOutput: Channel<LoginOutput> = Channel()
  val viewOutput = _viewOutput.receiveAsFlow()

  fun setInput(input: LoginInput) {
    when (input) {
//      is LoginInput.LoginButtonClicked -> login()
      is LoginInput.LoginButtonClicked -> sendOutput {
        LoginOutput.NavigateToMain(User("123", "Karim", "123@4.com", "photo"))
      }

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

    val newStateRenderer = StateRenderer.ScreenContent<LoginUiState, User>(loginUiState)
    _stateRendererMutableState.value = newStateRenderer
  }

  private fun sendOutput(action: () -> LoginOutput) {
    viewModelScope.launch {
      _viewOutput.send(action())
    }
  }

  private fun login() {
    viewModelScope.launch {
      // loading popup state
      val newStateRenderer = StateRenderer.LoadingPopup<LoginUiState, User>(loginUiState)
      _stateRendererMutableState.value = newStateRenderer
      loginUseCase.execute(
        LoginUseCase.Input(
          username = loginUiState.userName,
          password = loginUiState.password,
        ),
        success = {
          // loading popup state
          val newStateRenderer = StateRenderer.Success<LoginUiState, User>(it)
          _stateRendererMutableState.value = newStateRenderer
        },
        error = {
          // loading popup state
          val newStateRenderer =
            StateRenderer.ErrorPopup<LoginUiState, User>(loginUiState, it)
          _stateRendererMutableState.value = newStateRenderer
        },
      )
    }
  }
}
