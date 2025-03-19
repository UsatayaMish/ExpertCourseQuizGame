package com.usatayamish.expertcoursequizgame

import android.content.Context
import com.usatayamish.expertcoursequizgame.core.RunAsync
import com.usatayamish.expertcoursequizgame.load.data.cache.CacheModule

class Core(context: Context, val clearViewModel: ClearViewModel) {

    val runUiTests = false
    val sharedPreferences = context.getSharedPreferences(
        "quizAppData",
        Context.MODE_PRIVATE
    )
    val size = 10
    val cacheModule: CacheModule = CacheModule.Base(context)
    val runAsync: RunAsync = RunAsync.Base()
}

