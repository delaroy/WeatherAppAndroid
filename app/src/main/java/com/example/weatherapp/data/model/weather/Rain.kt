package com.example.weatherapp.data.model.weather

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Rain {
    @SerializedName("1h")
    @Expose
    private var _1h: Double? = null
    fun get1h(): Double? {
        return _1h
    }

    fun set1h(_1h: Double?) {
        this._1h = _1h
    }
}