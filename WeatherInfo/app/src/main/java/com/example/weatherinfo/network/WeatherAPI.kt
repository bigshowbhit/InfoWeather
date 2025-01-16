package com.example.weatherinfo.network

import com.example.weatherinfo.data.weather.WeatherResult
import retrofit2.http.GET
import retrofit2.http.Query

// https://api.exchangerate-api.com/v4/latest/USD

//Host: http://openweathermap.org/
//Path: data/2.5/weather
//Query params: we do not have now...

interface WeatherAPI {
    @GET("data/2.5/weather")
    suspend fun getWeatherData(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String): WeatherResult

}
