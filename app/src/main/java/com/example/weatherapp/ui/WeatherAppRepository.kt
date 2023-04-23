package com.example.weatherapp.ui

import com.example.weatherapp.data.WeatherAppService
import com.example.weatherapp.data.model.direct.GeoCodeResponse
import com.example.weatherapp.data.model.weather.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherAppRepository @Inject constructor(
    private val weatherAppService: WeatherAppService
) {

    suspend fun getCurrentWeather(lat: Double, lon: Double, appid: String, units: String): Response<CurrentWeatherResponse> = withContext(
        Dispatchers.IO) {
        val weatherResult = weatherAppService.getCurrentWeather(
            lat = lat,
            long = lon,
            appId = appid,
            units = units
        )
        weatherResult
    }

    suspend fun getDirectGeoCoding(cityName: String, appid: String): Response<List<GeoCodeResponse>> = withContext(
        Dispatchers.IO) {
        val geoResult = weatherAppService.getDirectGeoCoding(
            cityName = cityName,
            appId = appid
        )
        geoResult
    }
}
