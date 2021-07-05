package com.example.core

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val mockWebServer = MockWebServer()


    @Before
    fun setup() {
        mockWebServer.enqueue(MockResponse().setBody("{id:1000}"))
        mockWebServer.start(8080)
    }

    @After
    fun stop() {
        mockWebServer.shutdown()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("io.kiwiplus.app.jikimi.core.test", appContext.packageName)
    }
}
