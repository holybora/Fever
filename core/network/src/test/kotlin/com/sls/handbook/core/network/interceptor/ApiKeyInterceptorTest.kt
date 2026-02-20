package com.sls.handbook.core.network.interceptor

import com.sls.handbook.core.network.ApiKeyProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert.assertEquals
import org.junit.Test

class ApiKeyInterceptorTest {

    private val apiKeyProvider: ApiKeyProvider = mockk()
    private val interceptor = ApiKeyInterceptor(apiKeyProvider)

    @Test
    fun `adds appid query parameter to request`() {
        every { apiKeyProvider.getApiKey() } returns "test-api-key"
        val chain = mockChain("https://api.openweathermap.org/data/2.5/weather?lat=10.0")

        interceptor.intercept(chain)

        val captured = capturedRequest(chain)
        assertEquals("test-api-key", captured.url.queryParameter("appid"))
    }

    @Test
    fun `preserves existing query parameters`() {
        every { apiKeyProvider.getApiKey() } returns "key123"
        val chain = mockChain("https://api.openweathermap.org/data/2.5/weather?lat=10.0&lon=20.0")

        interceptor.intercept(chain)

        val captured = capturedRequest(chain)
        assertEquals("10.0", captured.url.queryParameter("lat"))
        assertEquals("20.0", captured.url.queryParameter("lon"))
        assertEquals("key123", captured.url.queryParameter("appid"))
    }

    @Test
    fun `calls chain proceed with modified request`() {
        every { apiKeyProvider.getApiKey() } returns "key"
        val chain = mockChain("https://example.com/api")

        interceptor.intercept(chain)

        verify(exactly = 1) { chain.proceed(any()) }
    }

    private fun mockChain(url: String): Interceptor.Chain {
        val request = Request.Builder().url(url).build()
        val chain: Interceptor.Chain = mockk()
        every { chain.request() } returns request
        every { chain.proceed(any()) } returns mockk<Response>()
        return chain
    }

    private fun capturedRequest(chain: Interceptor.Chain): Request {
        val slot = slot<Request>()
        verify { chain.proceed(capture(slot)) }
        return slot.captured
    }
}
