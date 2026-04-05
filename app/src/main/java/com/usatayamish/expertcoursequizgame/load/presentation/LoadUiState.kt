package com.usatayamish.expertcoursequizgame.load.presentation

import com.usatayamish.expertcoursequizgame.R
import com.usatayamish.expertcoursequizgame.game.NavigateToGame
import com.usatayamish.expertcoursequizgame.views.error.ErrorUiState
import com.usatayamish.expertcoursequizgame.views.error.UpdateError
import com.usatayamish.expertcoursequizgame.views.visiblebutton.UpdateVisibility
import com.usatayamish.expertcoursequizgame.views.visiblebutton.VisibilityUiState
import java.io.Serializable

interface LoadUiState : Serializable {

    fun show(
        errorTextView: UpdateError,
        retryButton: UpdateVisibility,
        progressBar: UpdateVisibility
    )

    fun navigate(navigateToGame: NavigateToGame) = Unit

    fun load(viewModel: LoadViewModel) = Unit

    abstract class Abstract(
        private val errorUiState: ErrorUiState,
        private val retryUiState: VisibilityUiState,
        private val progressUiState: VisibilityUiState
    ) : LoadUiState {

        override fun show(
            errorTextView: UpdateError,
            retryButton: UpdateVisibility,
            progressBar: UpdateVisibility
        ) {
            errorTextView.update(errorUiState)
            retryButton.update(retryUiState)
            progressBar.update(progressUiState)
        }
    }

    data class Progress(private val timeStamp: Long) : Abstract(
        errorUiState = ErrorUiState.Hide,
        retryUiState = VisibilityUiState.Gone,
        progressUiState = VisibilityUiState.Visible
    ) {
        override fun load(viewModel: LoadViewModel) {
            viewModel.loadInProgress(timeStamp)
        }
    }


    data object Success : Abstract(
        errorUiState = ErrorUiState.Hide,
        retryUiState = VisibilityUiState.Gone,
        progressUiState = VisibilityUiState.Gone
    ) {
        private fun readResolve(): Any = Success

        override fun navigate(navigateToGame: NavigateToGame) {
            navigateToGame.navigateToGame()
        }

    }

    data class Error(
        private val message: String
    ) : Abstract(
        errorUiState = ErrorUiState.Show(message),
        retryUiState = VisibilityUiState.Visible,
        progressUiState = VisibilityUiState.Gone
    )

    data class ErrorRes(val messageId: Int = R.string.no_internet_connection) : Abstract(
        errorUiState = ErrorUiState.ShowRes(messageId),
        retryUiState = VisibilityUiState.Visible,
        progressUiState = VisibilityUiState.Gone
    )

    data object Empty : LoadUiState {

        private fun readResolve(): Any = Empty

        override fun show(
            errorTextView: UpdateError,
            retryButton: UpdateVisibility,
            progressBar: UpdateVisibility
        ) = Unit

        override fun load(viewModel: LoadViewModel) {
            viewModel.load()
        }
    }
}
