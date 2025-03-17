package com.usatayamish.expertcoursequizgame.load.presentation

interface LoadUiObservable : UiObservable<LoadUiState> {
    class Base: UiObservable.Abstract<LoadUiState>(), LoadUiObservable
}