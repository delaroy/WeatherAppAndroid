package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.weatherapp.databinding.WeatherActivityBinding
import com.example.weatherapp.ui.WeatherViewModel
import com.example.weatherapp.ui.adapter.WeatherAppAdapter
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import com.example.weatherapp.util.API_KEY
import com.example.weatherapp.util.CustomProgressDialog
import com.example.weatherapp.util.Resource

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), WeatherAppAdapter.RecyclerViewClickListener {

    private val preFilledCity = listOf("Delhi", "Berlin", "Toronto")
    private val initiatePaymentAdapter: WeatherAppAdapter by lazy { WeatherAppAdapter(this) }
    private lateinit var binding: WeatherActivityBinding
    private val viewModel: WeatherViewModel by viewModels()
    private val progressDialog by lazy { CustomProgressDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//  set status text dark

        window.statusBarColor = ContextCompat.getColor(applicationContext,R.color.white)// set status background white

        binding = DataBindingUtil.setContentView(this, R.layout.weather_activity)
        binding.cityRecyclerview.apply {
            adapter = initiatePaymentAdapter
        }

        initiatePaymentAdapter.submitList(preFilledCity)

        viewModel.geoCodeData.observe(this) {
            when (it) {
                is Resource.Success -> {
                    progressDialog.stop()

                    val resp = it.data
                    val latitude = resp?.get(0)?.lat
                    val longitude = resp?.get(0)?.lon

                    val intent = Intent(this, WeatherDetailActivity::class.java)
                    intent.putExtra("lat", latitude)
                    intent.putExtra("lon", longitude)
                    startActivity(intent)
                }
                is Resource.Error -> {
                    progressDialog.stop()
                    it.message?.let { message ->
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    progressDialog.start(title = "initializing...")
                }
                else -> {}
            }
        }

        binding.search.setOnClickListener {
            val cityName = binding.cityField.text?.trim().toString()
            val lat = binding.latitudeField.text?.trim().toString()
            val lon = binding.longitudeField.text?.trim().toString()
            if (cityName.isNotEmpty()) {
                viewModel.getGeoCode(
                    cityName = cityName.lowercase(),
                    appid = API_KEY
                )
            } else if(lat.isEmpty()) {
                Toast.makeText(this, "Latitude is required", Toast.LENGTH_SHORT).show()
            } else if(lon.isEmpty()) {
                Toast.makeText(this, "Longitude is required", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, WeatherDetailActivity::class.java)
                intent.putExtra("lat", lat)
                intent.putExtra("lon", lon)
                startActivity(intent)
            }
            reset()
        }
    }

    private fun reset() {
        binding.cityField.setText("")
        binding.latitudeField.setText("")
        binding.longitudeField.setText("")
    }

    override fun clickOnItem(data: String) {
        viewModel.getGeoCode(
            cityName = data.lowercase(),
            appid = API_KEY
        )
    }
}