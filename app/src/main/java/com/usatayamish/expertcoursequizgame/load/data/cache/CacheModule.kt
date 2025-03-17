package com.usatayamish.expertcoursequizgame.load.data.cache

import android.content.Context
import androidx.room.Room
import com.usatayamish.expertcoursequizgame.R

interface CacheModule {

    fun dao(): QuestionAndChoicesDao

    fun clearDatabase(): ClearDatabase

    class Base(applicationContext: Context): CacheModule {

        private val database = Room.databaseBuilder(
            applicationContext,
            QuestionAndChoicesDatabase::class.java,
            applicationContext.getString(R.string.app_name)
        ).build()

        override fun dao(): QuestionAndChoicesDao = database.dao()

        override fun clearDatabase(): ClearDatabase = database
    }
}