package com.usatayamish.expertcoursequizgame

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.usatayamish.expertcoursequizgame.load.data.cache.IncorrectCache
import com.usatayamish.expertcoursequizgame.load.data.cache.QuestionAndChoicesDao
import com.usatayamish.expertcoursequizgame.load.data.cache.QuestionAndChoicesDatabase
import com.usatayamish.expertcoursequizgame.load.data.cache.QuestionCache
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var dao: QuestionAndChoicesDao
    private lateinit var database: QuestionAndChoicesDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            QuestionAndChoicesDatabase::class.java,
        ).build()
        dao = database.dao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun test() = runBlocking {
        dao.insertQuestions(
            listOf(
                QuestionCache(1, "1", "one"),
                QuestionCache(2, "2", "two")
            )
        )
        dao.insertIncorrects(
            listOf(
                IncorrectCache(questionId = 1, text = "incorrect11"),
                IncorrectCache(questionId = 1, text = "incorrect12"),
                IncorrectCache(questionId = 2, text = "incorrect21"),
                IncorrectCache(questionId = 2, text = "incorrect22"),
                IncorrectCache(questionId = 2, text = "incorrect23")
            )
        )

        var actual: Any = dao.question(1)
        var expected: Any = QuestionCache(1, "1", "one")
        assertEquals(expected, actual)

        actual = dao.question(2)
        expected = QuestionCache(2, "2", "two")
        assertEquals(expected, actual)

        actual = dao.incorrects(1)
        expected = listOf(
            IncorrectCache(questionId = 1, text = "incorrect11"),
            IncorrectCache(questionId = 1, text = "incorrect12")
        )
        assertEquals(expected, actual)

        actual = dao.incorrects(2)
        expected = listOf(
            IncorrectCache(questionId = 2, text = "incorrect21"),
            IncorrectCache(questionId = 2, text = "incorrect22"),
            IncorrectCache(questionId = 2, text = "incorrect23")
        )
        assertEquals(expected, actual)
    }
}