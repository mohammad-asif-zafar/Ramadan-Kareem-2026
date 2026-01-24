package com.hathway.ramadankareem2026.ui.mosques.data.remote

import com.google.gson.annotations.SerializedName

data class PlaceResult(
    @SerializedName("place_id")
    val placeId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("geometry")
    val geometry: Geometry,

    @SerializedName("vicinity")
    val vicinity: String?
)
