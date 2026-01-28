package com.hathway.ramadankareem2026.ui.quran.data.remote

import com.hathway.ramadankareem2026.core.network.ApiConfig
import com.hathway.ramadankareem2026.core.network.ApiResult
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class QuranRemoteDataSourceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var dataSource: QuranRemoteDataSource

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        // ✅ IMPORTANT: ApiConfig.QURAN_BASE_URL must be `var`
        ApiConfig.QURAN_BASE_URL = mockWebServer.url("/").toString()

        dataSource = QuranRemoteDataSource()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getSurahs returns Success when api responds 200`() = runTest {

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(
                    """
                    {
                      "code": 200,
                      "status": "OK",
                      "data": [
                        {
                          "number": 1,
                          "name": "الفاتحة",
                          "englishName": "Al-Faatiha",
                          "englishNameTranslation": "The Opening",
                          "numberOfAyahs": 7,
                          "revelationType": "Meccan"
                        }
                      ]
                    }
                    """.trimIndent()
                )
        )

        val result = dataSource.getSurahs()

        assertTrue(result is ApiResult.Success)

        val surahs = (result as ApiResult.Success).data
        assertEquals(1, surahs.size)
        assertEquals(1, surahs.first().number)
    }

    @Test
    fun `getSurahs returns Error on http 500`() = runTest {

        mockWebServer.enqueue(
            MockResponse().setResponseCode(500)
        )

        val result = dataSource.getSurahs()

        assertTrue(result is ApiResult.Error)
        assertEquals(500, (result as ApiResult.Error).code)
    }

    @Test
    fun `getSurahs returns Error on empty body`() = runTest {

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("")
        )

        val result = dataSource.getSurahs()

        assertTrue(result is ApiResult.Error)
    }
}
