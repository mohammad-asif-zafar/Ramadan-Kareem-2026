package com.hathway.ramadankareem2026.core.network
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class SafeApiCallTest {

    @Test
    fun `returns Success when response is successful`() = runBlocking {
        val response = Response.success("OK")

        val result = safeApiCall { response }

        assertTrue(result is ApiResult.Success)
        assertEquals("OK", (result as ApiResult.Success).data)
    }

    @Test
    fun `returns Error when response body is null`() = runBlocking {
        val response = Response.success<String>(null)

        val result = safeApiCall { response }

        assertTrue(result is ApiResult.Error)
        assertEquals("Empty body", (result as ApiResult.Error).message)
    }

    @Test
    fun `returns Error when http error`() = runBlocking {
        val errorBody = "Not found"
            .toResponseBody("application/json".toMediaType())

        val response = Response.error<String>(404, errorBody)

        val result = safeApiCall { response }

        assertTrue(result is ApiResult.Error)
        assertEquals(404, (result as ApiResult.Error).code)
    }

    @Test
    fun `returns Error on exception`() = runBlocking {
        val result = safeApiCall<String> {
            throw RuntimeException("Boom")
        }

        assertTrue(result is ApiResult.Error)
        assertEquals("RuntimeException: Boom", (result as ApiResult.Error).message)
    }

    @Test
    fun `returns generic message when exception has no message`() = runBlocking {
        val result = safeApiCall<String> {
            throw RuntimeException()
        }

        assertTrue(result is ApiResult.Error)
        assertEquals("RuntimeException: Unknown error", (result as ApiResult.Error).message)
    }
}
