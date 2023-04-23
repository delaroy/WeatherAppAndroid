package com.example.weatherapp.data

import com.example.weatherapp.data.model.direct.GeoCodeResponse
import com.example.weatherapp.data.model.weather.CurrentWeatherResponse
import com.example.weatherapp.util.BASE_URL_COORD
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAppService {

    @GET("weather")
    suspend fun getCurrentWeather(@Query("lat") lat: Double? = null, @Query("lon") long: Double? = null, @Query("appid") appId: String? = null, @Query("units") units: String? = null): Response<CurrentWeatherResponse>

    @GET(BASE_URL_COORD + "direct")
    suspend fun getDirectGeoCoding(@Query("q") cityName: String? = null, @Query("appid") appId: String? = null): Response<List<GeoCodeResponse>>

}
