package com.usatayamish.expertcoursequizgame.load

import com.google.gson.Gson
import com.usatayamish.expertcoursequizgame.load.data.cloud.QuizResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.net.HttpURLConnection
import java.net.URL

class LoadRepositoryTest {

    @Test
    fun test() {
        val url = "https://opentdb.com/api.php?amount=10&type=multiple"
        val connection = URL(url).openConnection() as HttpURLConnection
        try {
            val data = connection.inputStream.bufferedReader().use { it.readText() }
            assertTrue(data.isNotEmpty())

            val gson = Gson()
            val response = gson.fromJson(data, QuizResponse::class.java)
            val list = response.dataList
            assertEquals(10, list.size)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection.disconnect()
        }
    }
}

