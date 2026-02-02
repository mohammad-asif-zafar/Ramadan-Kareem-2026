package com.hathway.ramadankareem2026.core.network

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ApiResultExtensionsTest {

    @Test
    fun `map transforms Success data`() {
        val result = ApiResult.Success(2)

        val mapped = result.map { it * 3 }

        assertTrue(mapped is ApiResult.Success)
        assertEquals(6, (mapped as ApiResult.Success).data)
    }

    @Test
    fun `map does not transform Error`() {
        val error = ApiResult.Error("Network error", 500)

        val mapped = error.map { }

        assertTrue(mapped is ApiResult.Error)
        assertEquals("Network error", (mapped as ApiResult.Error).message)
        assertEquals(500, mapped.code)
    }

    @Test
    fun `map not executed when Error`() {
        val called = false

        val error = ApiResult.Error("Fail")

        error.map {
            it
        }

        assertFalse(called)
    }
}
