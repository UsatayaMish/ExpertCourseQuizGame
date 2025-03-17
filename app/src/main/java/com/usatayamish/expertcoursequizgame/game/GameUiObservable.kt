package com.usatayamish.expertcoursequizgame.game

import com.usatayamish.expertcoursequizgame.load.presentation.UiObservable

interface GameUiObservable: UiObservable<GameUiState> {
    class Base: UiObservable.Abstract<GameUiState>(), GameUiObservable
}