package com.usatayamish.expertcoursequizgame.load.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.usatayamish.expertcoursequizgame.ProvideViewModel
import com.usatayamish.expertcoursequizgame.databinding.FragmentLoadBinding
import com.usatayamish.expertcoursequizgame.game.NavigateToGame

class LoadFragment : Fragment() {

    private var _binding: FragmentLoadBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val update: (LoadUiState) -> Unit = { uiState ->


        uiState.show(
            binding.errorTextView,
            binding.retryButton,
            binding.progressBar
        )
        uiState.navigate(requireActivity() as NavigateToGame)

    }

    private lateinit var viewModel: LoadViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            (requireActivity() as ProvideViewModel).makeViewModel(LoadViewModel::class.java)

        binding.retryButton.setOnClickListener {
            viewModel.load()
        }

        viewModel.load(isFirstRun = savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        viewModel.startUpdates(observer = update)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopUpdates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}