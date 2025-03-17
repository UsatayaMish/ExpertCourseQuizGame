package com.usatayamish.expertcoursequizgame.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.usatayamish.expertcoursequizgame.ProvideViewModel
import com.usatayamish.expertcoursequizgame.core.AbstractFragment
import com.usatayamish.expertcoursequizgame.databinding.FragmentGameBinding
import com.usatayamish.expertcoursequizgame.stats.NavigateToGameOver


class GameFragment : AbstractFragment<GameUiState, GameViewModel>() {

    private var _binding: FragmentGameBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override val update: (GameUiState) -> Unit = { uiState ->
        uiState.update(
            binding.questionTextView,
            binding.firstChoiceButton,
            binding.secondChoiceButton,
            binding.thirdChoiceButton,
            binding.fourthChoiceButton,
            binding.nextButton,
            binding.checkButton
        )
        uiState.navigate(requireActivity() as NavigateToGameOver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (requireActivity() as ProvideViewModel).makeViewModel(GameViewModel::class.java)

        binding.firstChoiceButton.setOnClickListener {
            viewModel.chooseFirst()
        }

        binding.secondChoiceButton.setOnClickListener {
            viewModel.chooseSecond()
        }

        binding.thirdChoiceButton.setOnClickListener {
            viewModel.chooseThird()
        }

        binding.fourthChoiceButton.setOnClickListener {
            viewModel.chooseFourth()
        }

        binding.checkButton.setOnClickListener {
            viewModel.check()
        }


        binding.nextButton.setOnClickListener {
            viewModel.next()
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}