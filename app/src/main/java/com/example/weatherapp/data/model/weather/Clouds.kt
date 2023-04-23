package com.example.weatherapp.data.model.weather

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Clouds {
    @SerializedName("all")
    @Expose
    var all: Int? = null
}