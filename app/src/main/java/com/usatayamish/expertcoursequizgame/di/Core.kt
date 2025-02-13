package com.usatayamish.expertcoursequizgame

import android.content.Context
import com.google.gson.Gson

class Core(context: Context, val clearViewModel: ClearViewModel) {

    val runUiTests = true
    val sharedPreferences = context.getSharedPreferences(
        "quizAppData",
        Context.MODE_PRIVATE
    )
    val gson = Gson()

}