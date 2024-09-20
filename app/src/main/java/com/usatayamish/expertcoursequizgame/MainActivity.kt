package com.usatayamish.expertcoursequizgame

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.usatayamish.expertcoursequizgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: GameViewModel = GameViewModel(object : GameRepository {
            override fun questionAndChoices(): QuestionAndChoices {
                TODO("Not yet implemented")
            }

            override fun saveUserChoice(index: Int) {
                TODO("Not yet implemented")
            }

            override fun check(): CorrectAndIncorrectIndexes {
                TODO("Not yet implemented")
            }

            override fun next() {
                TODO("Not yet implemented")
            }

        })

        binding.firstChoiceButton.setOnClickListener {
            val uiState: GameUiState = viewModel.chooseFirst()
            uiState.update(binding = binding)
        }

        binding.secondChoiceButton.setOnClickListener {
            val uiState: GameUiState = viewModel.chooseSecond()
            uiState.update(binding = binding)
        }

        binding.thirdChoiceButton.setOnClickListener {
            val uiState: GameUiState = viewModel.chooseThird()
            uiState.update(binding = binding)
        }

        binding.fourthChoiceButton.setOnClickListener {
            val uiState: GameUiState = viewModel.chooseFourth()
            uiState.update(binding = binding)
        }

        binding.checkButton.setOnClickListener {
            val uiState: GameUiState = viewModel.check()
            uiState.update(binding = binding)
        }

        binding.nextButton.setOnClickListener {
            val uiState: GameUiState = viewModel.next()
            uiState.update(binding = binding)
        }

        val uiState: GameUiState = viewModel.init()
        uiState.update(binding = binding)
    }



}