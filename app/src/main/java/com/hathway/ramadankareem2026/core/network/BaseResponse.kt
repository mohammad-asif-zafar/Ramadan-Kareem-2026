package com.hathway.ramadankareem2026.core.network

data class BaseResponse<T>(
    val code: Int,
    val status: String,
    val data: T
)
