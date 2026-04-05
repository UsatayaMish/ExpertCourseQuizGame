package com.usatayamish.expertcoursequizgame.load.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.usatayamish.expertcoursequizgame.ProvideViewModel
import com.usatayamish.expertcoursequizgame.core.AbstractFragment
import com.usatayamish.expertcoursequizgame.databinding.FragmentLoadBinding
import com.usatayamish.expertcoursequizgame.game.NavigateToGame

class LoadFragment : AbstractFragment<LoadUiState, LoadViewModel>() {

    private var _binding: FragmentLoadBinding? = null
    private var cachedUiState: LoadUiState = LoadUiState.Empty

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

    override val update: (LoadUiState) -> Unit = { uiState ->
        cachedUiState = uiState
        uiState.show(
            binding.errorTextView,
            binding.retryButton,
            binding.progressBar
        )
        uiState.navigate(requireActivity() as NavigateToGame)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            (requireActivity() as ProvideViewModel).makeViewModel(LoadViewModel::class.java)

        binding.retryButton.setOnClickListener {
            viewModel.load()
        }

        savedInstanceState?.let {
            cachedUiState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getSerializable(KEY, LoadUiState::class.java) as LoadUiState
            } else  {
                it.getSerializable(KEY) as LoadUiState
            }
        }
        cachedUiState.load(viewModel)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, cachedUiState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val KEY = "uiState"
    }

}