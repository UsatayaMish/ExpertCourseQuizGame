package com.usatayamish.expertcoursequizgame.core

import androidx.fragment.app.Fragment

abstract class AbstractFragment<UiState : Any, T: MyViewModel.Async<UiState>> : Fragment(){

    protected lateinit var viewModel: T

    protected abstract val update: (UiState) -> Unit

    override fun onResume() {
        super.onResume()
        viewModel.startUpdates(observer = update)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopUpdates()
    }
}