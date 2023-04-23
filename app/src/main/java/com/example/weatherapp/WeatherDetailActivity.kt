package com.example.weatherapp

import android.os.Bundle
import android.view.View
import android.view.View.NO_ID
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.weatherapp.databinding.WeatherDetailBinding
import com.example.weatherapp.ui.WeatherViewModel
import com.example.weatherapp.util.API_KEY
import com.example.weatherapp.util.CustomProgressDialog
import com.example.weatherapp.util.Resource
import com.example.weatherapp.util.milliToDate
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WeatherDetailActivity: AppCompatActivity() {

    private lateinit var binding: WeatherDetailBinding
    private val viewModel: WeatherViewModel by viewModels()
    private val progressDialog by lazy { CustomProgressDialog(this) }
    private var materialButtonToggleGroup: MaterialButtonToggleGroup? = null

    private var metricValue = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//  set status text dark

        window.statusBarColor = ContextCompat.getColor(applicationContext,R.color.white)// set status background white

        binding = DataBindingUtil.setContentView(this, R.layout.weather_detail)

        val latitude = intent.getDoubleExtra("lat", 0.0)
        val longitude = intent.getDoubleExtra("lon", 0.0)

        materialButtonToggleGroup = binding.toggleButton
        materialButtonToggleGroup!!.check(R.id.celsius)

        searchWeather(
            latitude = latitude,
            longitude = longitude,
            units = "metric"
        )

        viewModel.currentWeatherData.observe(this) {
            when (it) {
                is Resource.Success -> {
                    progressDialog.stop()

                    val resp = it.data

                    binding.city.text = resp?.name
                    binding.weatherType.text = resp?.weather?.get(0)?.main
                    val degree = 0x00B0.toChar()
                    if (metricValue == "metric") {
                        binding.temp.text = resp?.main?.temp.toString() + degree + "C"
                    } else if (metricValue == "imperial") {
                        binding.temp.text = resp?.main?.temp.toString() + degree + "F"
                    }
                    binding.minTempTxt.text = resp?.main?.tempMin.toString()
                    binding.maxTempTxt.text = resp?.main?.tempMax.toString()
                    binding.cloudCoverageTxt.text = resp?.clouds?.all.toString()
                    binding.latitudeTxt.text = resp?.coord?.lat.toString()
                    binding.longitudeTxt.text = resp?.coord?.lon.toString()
                    binding.sunriseTxt.text = milliToDate(resp?.sys?.sunrise ?: 0)
                    binding.sunSetTxt.text = milliToDate( resp?.sys?.sunset ?: 0)

                    val image = resp?.weather?.get(0)?.icon
                    val iconUrl = "http://openweathermap.org/img/w/$image.png";

                    Glide.with(binding.weatherImg)
                        .load(iconUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.weatherImg)

                }
                is Resource.Error -> {
                    progressDialog.stop()
                    it.message?.let { message ->
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    progressDialog.start(title = "fetching...")
                }
                else -> {}
            }
        }

        binding.toggleButton.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->

            val checkedButtonId = materialButtonToggleGroup!!.checkedButtonId
            if (checkedButtonId != NO_ID) {
                val checkedButton = findViewById<MaterialButton>(checkedButtonId)
                val buttonText = checkedButton.text.toString()
                Toast.makeText(this, buttonText, Toast.LENGTH_SHORT).show()
                if (buttonText.lowercase() == "c") {
                    searchWeather(
                        latitude = latitude,
                        longitude = longitude,
                        units = "metric"
                    )
                } else if (buttonText.lowercase() == "f") {
                    searchWeather(
                        latitude = latitude,
                        longitude = longitude,
                        units = "imperial"
                    )
                }
            }
        }
    }

    private fun searchWeather(latitude: Double, longitude: Double, units: String = "metric") {
        metricValue = units
        viewModel.currentWeather(
            lat = latitude,
            lon = longitude,
            appid = API_KEY,
            units = units
        )
    }
}