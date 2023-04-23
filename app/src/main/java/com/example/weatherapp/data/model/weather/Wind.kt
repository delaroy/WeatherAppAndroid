package com.example.weatherapp.data.model.weather

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Wind {
    @SerializedName("speed")
    @Expose
    var speed: Double? = null

    @SerializedName("deg")
    @Expose
    var deg: Int? = null

    @SerializedName("gust")
    @Expose
    var gust: Double? = null
}