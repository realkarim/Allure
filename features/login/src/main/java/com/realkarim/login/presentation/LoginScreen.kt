import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.realkarim.domain.model.toJson
import com.realkarim.login.R
import com.realkarim.login.presentation.LoginViewModel
import com.realkarim.login.presentation.action.LoginInput
import com.realkarim.login.presentation.action.LoginOutput
import com.realkarim.login.presentation.action.LoginUiState
import com.realkarim.navigator.AppNavigator
import com.realkarim.navigator.destination.HomeDestination
import com.realkarim.navigator.destination.Screens
import com.realkarim.presentation.StateRenderer
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun LoginScreen(appNavigator: AppNavigator) {
  val loginViewModel: LoginViewModel = hiltViewModel()
  val stateRenderer by loginViewModel.stateRendererFlow.collectAsState()

  LaunchedEffect(loginViewModel) {
    loginViewModel.viewOutput.collect { output ->
      when (output) {
        is LoginOutput.NavigateToMain -> {
          val mainOutput = output
          appNavigator.navigate(
            HomeDestination.createHome(
              user = mainOutput.user.toJson(),
              age = 36,
              fullName = mainOutput.user.fullName,
            ),
          )
        }

        is LoginOutput.NavigateToRegister -> {
          appNavigator.navigate(Screens.SignUpScreenRoute.route)
        }

        is LoginOutput.ShowError -> {
          TODO()
        }
      }
    }
  }

  StateRenderer.of(
    statRenderer = stateRenderer,
    retryAction = { loginViewModel.setInput(LoginInput.LoginButtonClicked) },
  ) {
    onUiState { updatedState ->
      LoginScreen(updatedState, loginViewModel)
    }
    onLoadingState { updatedState ->
      LoginScreen(updatedState, loginViewModel)
    }
    onSuccessState { user ->
      val encodedUserJson =
        URLEncoder.encode(user.toJson(), StandardCharsets.UTF_8.toString())
      appNavigator.navigate(
        HomeDestination.createHome(
          user = encodedUserJson,
          age = 33,
          fullName = user.fullName,
        ),
      )
    }
    onEmptyState {
    }
    onErrorState { updatedState ->
      LoginScreen(updatedState, loginViewModel)
    }
  }
}

@Composable
fun LoginScreen(loginUiState: LoginUiState, loginViewModel: LoginViewModel) {
  Surface(modifier = Modifier.fillMaxSize()) {
    Column(
      modifier = Modifier.padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
    ) {
      CustomTextField(
        label = stringResource(id = R.string.username_label),
        value = loginUiState.userName,
        errorText = stringResource(id = loginUiState.userNameError.getErrorMessage()),
        showError = loginUiState.showUsernameError(),
      ) { userName ->
        loginViewModel.setInput(LoginInput.UserNameUpdated(userName))
      }
      Spacer(modifier = Modifier.height(16.dp))
      CustomTextField(
        label = stringResource(id = R.string.password_label),
        value = loginUiState.password,
        errorText = stringResource(id = loginUiState.passwordError.getErrorMessage()),
        showError = loginUiState.showPasswordError(),
      ) { password ->
        loginViewModel.setInput(LoginInput.PasswordUpdated(password))
      }
      Spacer(modifier = Modifier.height(16.dp))

      Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { loginViewModel.setInput(LoginInput.LoginButtonClicked) },
      ) {
        Text(text = "Login")
      }
      Spacer(modifier = Modifier.height(16.dp))
      TextButton(onClick = { loginViewModel.setInput(LoginInput.RegisterButtonClicked) }) {
        Text(text = "Sign up Now!")
      }
    }
  }
}

@Composable
fun CustomTextField(
  label: String,
  value: String,
  showError: Boolean,
  errorText: String,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  onChanged: (String) -> Unit,
) {
  OutlinedTextField(
    value = value,
    onValueChange = { onChanged(it) },
    label = { Text(text = label) },
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp),
    isError = showError,
    visualTransformation = visualTransformation,
  )
  if (showError) {
    Text(
      text = errorText,
      color = Color.Red,
      modifier = Modifier.padding(all = 8.dp),
    )
  }
}
