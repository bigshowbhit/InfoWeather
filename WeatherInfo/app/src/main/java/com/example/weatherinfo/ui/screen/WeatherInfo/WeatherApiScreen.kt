package com.example.weatherinfo.ui.screen.WeatherInfo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherinfo.data.weather.WeatherResult

@Composable
fun WeatherApiScreen(
    weatherViewModel: WeatherApiViewModel = hiltViewModel(),cityName: String
) {
    LaunchedEffect(cityName) {
        weatherViewModel.getWeather(cityName, "metric", "1ddd56f888c8e772c34612a5dd3520b4")
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = {
            weatherViewModel.getWeather(cityName, "metric", "1ddd56f888c8e772c34612a5dd3520b4")
        }) {
            Text(text = "Refresh")
        }
        when (weatherViewModel.weatherUiState) {
            is WeatherUiState.Init -> Text(text = "Press refresh")
            is WeatherUiState.Loading -> CircularProgressIndicator()
            is WeatherUiState.Success -> WeatherResultScreen((weatherViewModel.weatherUiState as WeatherUiState.Success).weatherData)
            is WeatherUiState.Error -> Text(text = "Error...")
        }
    }
}

@Composable
fun WeatherResultScreen(weatherData: WeatherResult) {
    Column() {
        Text(text = "Weather Data")
        Text(text = "City: ${weatherData.name}")
        Text(text = "Current temperature: ${weatherData.main?.temp}")
        Text(text = "Timezone: ${weatherData.timezone}")

    }
}