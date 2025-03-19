package com.usatayamish.expertcoursequizgame.load.presentation

import com.usatayamish.expertcoursequizgame.R
import com.usatayamish.expertcoursequizgame.game.NavigateToGame
import com.usatayamish.expertcoursequizgame.views.error.ErrorUiState
import com.usatayamish.expertcoursequizgame.views.error.UpdateError
import com.usatayamish.expertcoursequizgame.views.visiblebutton.UpdateVisibility
import com.usatayamish.expertcoursequizgame.views.visiblebutton.VisibilityUiState

interface LoadUiState {

    fun show(
        errorTextView: UpdateError,
        retryButton: UpdateVisibility,
        progressBar: UpdateVisibility
    )

    fun navigate(navigateToGame: NavigateToGame) = Unit

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

    object Progress : Abstract(
        errorUiState = ErrorUiState.Hide,
        retryUiState = VisibilityUiState.Gone,
        progressUiState = VisibilityUiState.Visible
    )


    object Success : Abstract(
        errorUiState = ErrorUiState.Hide,
        retryUiState = VisibilityUiState.Gone,
        progressUiState = VisibilityUiState.Gone
    ) {
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

    data class ErrorRes(val messageId: Int = R.string.no_internet_connection): Abstract(
        errorUiState = ErrorUiState.ShowRes(messageId),
        retryUiState = VisibilityUiState.Visible,
        progressUiState = VisibilityUiState.Gone
    )
}
