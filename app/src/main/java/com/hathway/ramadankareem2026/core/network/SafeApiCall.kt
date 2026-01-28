package com.hathway.ramadankareem2026.core.network

import retrofit2.Response
suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>
): ApiResult<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                ApiResult.Success(it)
            } ?: ApiResult.Error("Empty body", response.code())
        } else {
            ApiResult.Error(response.message(), response.code())
        }
    } catch (e: Exception) {
        ApiResult.Error(e.localizedMessage ?: "Unknown error")
    }
}

