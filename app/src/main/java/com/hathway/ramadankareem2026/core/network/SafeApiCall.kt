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
            val errorBody = try {
                response.errorBody()?.string()
            } catch (_: Exception) {
                null
            }

            val message = buildString {
                append(response.message())
                if (!errorBody.isNullOrBlank()) {
                    append(" | ")
                    append(errorBody)
                }
            }.ifBlank { "HTTP ${response.code()}" }

            ApiResult.Error(message, response.code())
        }
    } catch (e: Exception) {
        val msg = e.localizedMessage ?: "Unknown error"
        ApiResult.Error("${e.javaClass.simpleName}: $msg")
    }
}

