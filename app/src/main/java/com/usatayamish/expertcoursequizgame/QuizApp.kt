package com.usatayamish.expertcoursequizgame

import android.app.Application

class QuizApp : Application() {

    lateinit var viewmodel: GameViewModel

    override fun onCreate() {
        super.onCreate()
        viewmodel = GameViewModel(GameRepository.Base())

    }
}

