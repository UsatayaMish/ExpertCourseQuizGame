package com.usatayamish.expertcoursequizgame.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.usatayamish.expertcoursequizgame.ProvideViewModel
import com.usatayamish.expertcoursequizgame.databinding.FragmentGameOverBinding
import com.usatayamish.expertcoursequizgame.load.presentation.NavigateToLoad


class GameOverFragment : Fragment() {

    private var _binding: FragmentGameOverBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameOverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: GameOverViewModel = (requireActivity() as ProvideViewModel).makeViewModel(GameOverViewModel::class.java)


        binding.newGameButton.setOnClickListener {
            viewModel.clear()
            (requireActivity() as NavigateToLoad).navigateToLoad()
        }
        val uiState = viewModel.init(savedInstanceState == null)
        binding.statsTextView.update(uiState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}
