package com.usatayamish.expertcoursequizgame.load.di

import com.usatayamish.expertcoursequizgame.Core
import com.usatayamish.expertcoursequizgame.Module
import com.usatayamish.expertcoursequizgame.ProvideViewModel
import com.usatayamish.expertcoursequizgame.RunAsync
import com.usatayamish.expertcoursequizgame.di.AbstractProvideViewModel
import com.usatayamish.expertcoursequizgame.load.data.LoadRepository
import com.usatayamish.expertcoursequizgame.load.data.ParseQuestionAndChoices
import com.usatayamish.expertcoursequizgame.load.data.QuizResponse
import com.usatayamish.expertcoursequizgame.load.data.QuizService
import com.usatayamish.expertcoursequizgame.load.data.StringCache
import com.usatayamish.expertcoursequizgame.load.presentation.LoadViewModel
import com.usatayamish.expertcoursequizgame.load.presentation.UiObservable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ProvideLoadViewModel(
    core: Core,
    next: ProvideViewModel
): AbstractProvideViewModel(
    core,
    next,
    LoadViewModel::class.java
) {

    override fun module(): Module<*> = LoadModule(core)
}

class LoadModule(
    private val core: Core
) : Module<LoadViewModel> {

    override fun viewModel(): LoadViewModel {
        val defaultResponse = core.gson.toJson(QuizResponse(-1, emptyList()))
        val dataCache = StringCache.Base(core.sharedPreferences, "responseData", defaultResponse)
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://opentdb.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(QuizService::class.java)
        return LoadViewModel(
            LoadRepository.Base(
                service,
                ParseQuestionAndChoices.Base(core.gson),
                dataCache
            ),
            UiObservable.Base(),
            RunAsync.Base()
        )
    }
}