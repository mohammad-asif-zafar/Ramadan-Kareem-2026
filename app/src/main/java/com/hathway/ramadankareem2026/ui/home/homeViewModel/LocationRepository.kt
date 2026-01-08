package com.hathway.ramadankareem2026.ui.home.homeViewModel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.LocationServices
import com.hathway.ramadankareem2026.ui.qibla.Quadruple
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume

class LocationRepository(private val context: Context) {

    private val client = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getCityAndCountry(): Pair<String, String>? = suspendCancellableCoroutine { cont ->

        client.lastLocation.addOnSuccessListener { location ->
            if (location == null) {
                cont.resume(null)
                return@addOnSuccessListener
            }

            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(
                location.latitude, location.longitude, 1
            )

            if (!addresses.isNullOrEmpty()) {
                val city = addresses[0].locality ?: ""
                val country = addresses[0].countryName ?: ""
                cont.resume(city to country)
            } else {
                cont.resume(null)
            }
        }.addOnFailureListener {
            cont.resume(null)
        }
    }

    @SuppressLint("MissingPermission")

    /**
     * Returns city, country, latitude, longitude
     */
    suspend fun getCityAndCountryWithLatLng(): Quadruple<String, String, Double, Double>? =
        suspendCancellableCoroutine { cont ->

            client.lastLocation.addOnSuccessListener { location ->
                    if (location == null) {
                        cont.resume(null)
                        return@addOnSuccessListener
                    }

                    val lat = location.latitude
                    val lng = location.longitude

                    val geocoder = Geocoder(context, Locale.getDefault())

                    val addresses = geocoder.getFromLocation(lat, lng, 1)

                    if (!addresses.isNullOrEmpty()) {
                        val address = addresses[0]
                        val city = address.locality ?: address.subAdminArea ?: ""
                        val country = address.countryName ?: ""

                        cont.resume(
                            Quadruple(
                                city, country, lat, lng
                            )
                        )
                    } else {
                        cont.resume(null)
                    }
                }.addOnFailureListener {
                    cont.resume(null)
                }
        }
}

