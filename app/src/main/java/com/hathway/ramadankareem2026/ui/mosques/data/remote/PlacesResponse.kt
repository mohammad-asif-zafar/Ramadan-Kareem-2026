package com.hathway.ramadankareem2026.ui.mosques.data.remote

import com.google.gson.annotations.SerializedName

data class PlacesResponse(
    @SerializedName("results")
    val results: List<PlaceResult>,

    @SerializedName("status")
    val status: String
)
