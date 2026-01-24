package com.hathway.ramadankareem2026.ui.mosques.data.remote

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("lat")
    val lat: Double,

    @SerializedName("lng")
    val lng: Double
)
