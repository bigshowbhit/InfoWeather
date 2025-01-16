package com.example.weatherinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherinfo.ui.navigation.Screen
import com.example.weatherinfo.ui.screen.MainScreen
import com.example.weatherinfo.ui.screen.WeatherInfo.WeatherApiScreen
import com.example.weatherinfo.ui.theme.WeatherInfoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherInfoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    (
                            NavGraph(
                                modifier = Modifier.padding(innerPadding)
                            )
                            )
                }
            }
        }
    }
}

@Composable
fun NavGraph(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
    NavHost(modifier = modifier, navController = navController, startDestination = Screen.Main.route) {
        composable(route = Screen.Main.route) {
            MainScreen(
                onNavigateToWeather = { cityName ->
                    navController.navigate("${Screen.Weather.route}/$cityName")
                }
            )
        }
        composable(route = "${Screen.Weather.route}/{cityName}") { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName") ?: "Budapest"
            WeatherApiScreen(cityName = cityName)
        }
    }
}



