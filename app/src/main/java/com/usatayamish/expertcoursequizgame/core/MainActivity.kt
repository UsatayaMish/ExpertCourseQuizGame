package com.usatayamish.expertcoursequizgame.core


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.usatayamish.expertcoursequizgame.ProvideViewModel
import com.usatayamish.expertcoursequizgame.R
import com.usatayamish.expertcoursequizgame.game.GameScreen
import com.usatayamish.expertcoursequizgame.game.NavigateToGame
import com.usatayamish.expertcoursequizgame.load.presentation.LoadScreen
import com.usatayamish.expertcoursequizgame.load.presentation.NavigateToLoad
import com.usatayamish.expertcoursequizgame.stats.GameOverScreen
import com.usatayamish.expertcoursequizgame.stats.NavigateToGameOver
class MainActivity : AppCompatActivity(), Navigate, ProvideViewModel {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel = makeViewModel(MainViewModel::class.java)
        val screen = viewModel.firstScreen(savedInstanceState == null)
        navigate(screen)

    }

    override fun navigate(screen: Screen) {
        screen.show(R.id.container, supportFragmentManager)
    }

    override fun <S: Any, T : MyViewModel<S>> makeViewModel(clasz: Class<T>): T {
        return  (application as ProvideViewModel).makeViewModel(clasz)
    }


}

interface Navigate: NavigateToGame, NavigateToGameOver, NavigateToLoad {

    fun navigate(screen: Screen)

    override fun navigateToGameOver() = navigate(GameOverScreen)


    override fun navigateToGame() = navigate(GameScreen)

    override fun navigateToLoad() = navigate(LoadScreen)

}

