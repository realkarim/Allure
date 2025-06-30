package com.realkarim.allure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.realkarim.allure.ui.theme.AllureTheme
import com.realkarim.provider.DataProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AllureTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = DataProvider.userName,
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(16.dp),
                    )
                }
            }
        }

        // Test dependency availability
//          val room = Room.databaseBuilder()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AllureTheme {
        Greeting("Android")
    }
}
