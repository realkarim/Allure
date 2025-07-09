package com.realkarim.allure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.realkarim.allure.ui.theme.AllureTheme
import com.realkarim.datastore.settings.AppSettings
import com.realkarim.datastore.settings.AppSettingsSerializer
import com.realkarim.datastore.settings.Language
import com.realkarim.datastore.settings.Location
import com.realkarim.protodatastore.manager.preference.PreferencesDataStore
import com.realkarim.protodatastore.manager.session.SessionDataStore
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  lateinit var appSettingDataStore: DataStore<AppSettings>

  @Inject
  lateinit var preferencesDataStore: PreferencesDataStore

  @Inject
  lateinit var sessionDataStore: SessionDataStore

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    appSettingDataStore = DataStoreFactory.create(
      serializer = AppSettingsSerializer(),
      produceFile = { dataStoreFile("app_settings.json") },
      scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    )

    enableEdgeToEdge()
    setContent {
      AllureTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          SettingsScreen(
            sessionDataStore,
            appSettingDataStore,
            Modifier.padding(innerPadding),
          )
        }
      }
    }

    // Test dependency availability
//          val room = Room.databaseBuilder()
  }
}

@Composable
fun Greetings(name: String, modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    Text(
      text = "Hello $name!",
      modifier = modifier,
    )
    Text(
      text = "Base Url: ${BuildConfig.BASE_URL}",
      modifier = modifier,
    )
    Text(
      text = "DB Version: ${BuildConfig.DB_VERSION}",
      modifier = modifier,
    )
    Text(
      text = "Can Clear Cache: ${BuildConfig.CAN_CLEAR_CACHE}",
      modifier = modifier,
    )
    Text(
      text = "API Key: ${BuildConfig.API_KEY}",
      modifier = modifier,
    )
  }
}

@Composable
fun SettingsScreen(
  sessionDataStore: SessionDataStore,
  appSettingDataStore: DataStore<AppSettings>,
  modifier: Modifier,
) {
  val scope = rememberCoroutineScope()
  val appSettings by appSettingDataStore.data.collectAsState(initial = AppSettings())

  val accessTokenFlow by sessionDataStore.getAccessTokenFlow()
    .collectAsState(initial = "")

  var accessTokenValue by remember { mutableStateOf("") }

  Column(modifier = modifier.padding(50.dp)) {
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = "accessTokenFlow: $accessTokenFlow")
    Spacer(modifier = Modifier.height(16.dp))

    LaunchedEffect(Unit) {
      scope.launch {
        accessTokenValue = sessionDataStore.getAccessToken()
      }
    }

    Text(text = "accessTokenValue: $accessTokenValue")

    Spacer(modifier = Modifier.height(16.dp))

    Button(onClick = {
      scope.launch {
        sessionDataStore.setAccessToken("Access Token " + System.currentTimeMillis())
      }
    }) {
      Text(text = "Insert")
    }

    // display saved language
    Text(text = "Language: " + appSettings.language)
    Spacer(modifier = Modifier.height(16.dp))

    // display saved locations
    Text(text = "Last known locations: ")
    appSettings.lastKnownLocations.forEach { location ->
      Spacer(modifier = Modifier.height(16.dp))
      Text(text = "Lat: ${location.lat} Lng: ${location.long}")
    }
    Spacer(modifier = Modifier.height(16.dp))
    val newLocation = Location(37.123, 122.908)

    // create drop down menu to display language options and set location as well
    Language.entries.forEach { language ->
      DropdownMenuItem(text = { Text(text = language.name) }, onClick = {
        scope.launch {
          appSettingDataStore.updateData { currentSettings ->
            currentSettings.copy(
              language = language,
              lastKnownLocations = currentSettings.lastKnownLocations.add(
                newLocation,
              ),
            )
          }
        }
      })
    }
  }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  AllureTheme {
//    Greeting("Android")
  }
}
