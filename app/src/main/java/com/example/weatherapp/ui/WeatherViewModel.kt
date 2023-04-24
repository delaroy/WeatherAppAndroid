package com.example.weatherapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.direct.GeoCodeResponse
import com.example.weatherapp.data.model.weather.CurrentWeatherResponse
import com.example.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherAppRepository: WeatherAppRepository
) : ViewModel() {

    val currentWeatherData: MutableLiveData<Resource<CurrentWeatherResponse>> = MutableLiveData()
    val geoCodeData: MutableLiveData<Resource<List<GeoCodeResponse>>> = MutableLiveData()

    fun currentWeather(lat: Double, lon: Double, appid: String, units: String) {
        currentWeatherData.postValue(Resource.Loading())
        viewModelScope.launch {
            var response : Response<CurrentWeatherResponse>? = null
            try {
                response = weatherAppRepository.getCurrentWeather(lat, lon, appid, units)
                currentWeatherData.postValue(Resource.Success(response.body()!!))
            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> currentWeatherData.postValue(Resource.Error("Connection error, please try again or check internet connection"))
                    else -> {
                        currentWeatherData.postValue(Resource.Error("something went wrong!"))
                    }
                }
            }
        }
    }

    fun getGeoCode(cityName: String, appid: String) {
        geoCodeData.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                val response = weatherAppRepository.getDirectGeoCoding(cityName, appid)
                if (response.isSuccessful) {
                    geoCodeData.postValue(Resource.Success(response.body()!!))
                }
            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> geoCodeData.postValue(Resource.Error("Connection error, please try again or check internet connection"))
                    else -> {
                        geoCodeData.postValue(Resource.Error(  ex.message.toString()))
                    }
                }
            }
        }
    }

}
