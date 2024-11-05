package com.usatayamish.expertcoursequizgame

import android.app.Application
import android.content.Context
import com.usatayamish.expertcoursequizgame.game.GameRepository
import com.usatayamish.expertcoursequizgame.game.GameViewModel
import com.usatayamish.expertcoursequizgame.stats.GameOverViewModel


class QuizApp : Application() {


    lateinit var gameViewModel: GameViewModel
    lateinit var gameOverViewModel: GameOverViewModel

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = getSharedPreferences(
            "quizAppData",
            Context.MODE_PRIVATE
        )
        gameViewModel = GameViewModel(
            GameRepository.Base(
            IntCache.Base(sharedPreferences, "indexKey", 0),
            IntCache.Base(sharedPreferences, "userChoiceIndexKey", -1)
        ))
        //gameOverViewModel = GameOverViewModel()
    }
}


