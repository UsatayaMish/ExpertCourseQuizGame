package com.usatayamish.expertcoursequizgame

import android.content.Context

class Core(context: Context, val clearViewModel: ClearViewModel) {

    val sharedPreferences = context.getSharedPreferences(
        "quizAppData",
        Context.MODE_PRIVATE
    )
}