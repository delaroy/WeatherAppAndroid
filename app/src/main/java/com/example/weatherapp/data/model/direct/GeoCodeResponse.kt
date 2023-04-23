package com.example.weatherapp.data.model.direct

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class GeoCodeResponse {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("lat")
    @Expose
    var lat: Double? = null

    @SerializedName("lon")
    @Expose
    var lon: Double? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("state")
    @Expose
    var state: String? = null
}