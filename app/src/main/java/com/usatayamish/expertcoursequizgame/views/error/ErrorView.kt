package com.usatayamish.expertcoursequizgame.views.error

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class ErrorView : AppCompatTextView,  UpdateError{

    private lateinit var state: ErrorUiState

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = ErrorSavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as ErrorSavedState
        super.onRestoreInstanceState(restoredState.superState)
        update(restoredState.restore())
    }

    override fun update(uiState: ErrorUiState) {
        state = uiState
        uiState.update(this)
    }

    override fun updateText(textResId: Int) {
        setText(textResId)
    }

    override fun updateText(text: String) {
        setText(text)
    }

    override fun updateVisibility(visibility: Int) {
        this.visibility = visibility
    }

}

interface UpdateError {

    fun update(uiState: ErrorUiState)

    fun updateText(textResId: Int)

    fun updateText(text: String)

    fun updateVisibility(visibility: Int)
}
