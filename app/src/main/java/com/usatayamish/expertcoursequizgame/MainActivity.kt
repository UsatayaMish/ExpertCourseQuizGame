package com.usatayamish.expertcoursequizgame


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.usatayamish.expertcoursequizgame.game.GameScreen
import com.usatayamish.expertcoursequizgame.game.NavigateToGame
import com.usatayamish.expertcoursequizgame.stats.GameOverScreen
import com.usatayamish.expertcoursequizgame.stats.NavigateToGameOver

class MainActivity : AppCompatActivity(), Navigate {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            navigateToGame()
        }

    }

    override fun navigate(screen: Screen) {
        screen.show(R.id.container, supportFragmentManager)
    }




}

interface Navigate: NavigateToGame, NavigateToGameOver {

    fun navigate(screen: Screen)

    override fun navigateToGameOver() {
        navigate(GameOverScreen)
    }

    override fun navigateToGame() {
        navigate(GameScreen)
    }
}

