package com.example.weatherinfo.ui.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Weather : Screen("weather")
}