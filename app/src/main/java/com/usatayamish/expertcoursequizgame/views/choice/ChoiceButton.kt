package com.usatayamish.expertcoursequizgame.views.choice

import android.content.Context
import android.graphics.Color
import android.os.Parcelable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.usatayamish.expertcoursequizgame.views.question.UpdateText

class ChoiceButton : AppCompatButton, UpdateChoiceButton {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getFreezesText() = true

    private lateinit var state: ChoiceUiState

    override fun update(state: ChoiceUiState) {
        this.state = state
        state.update(this)
    }

    override fun update(color: String, clickable: Boolean, enabled: Boolean) {
        setBackgroundColor(Color.parseColor(color))
        isClickable = clickable
        isEnabled = enabled
    }

    override fun update(text: String) {
        this.text = text
    }

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = ChoiceButtonSavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as ChoiceButtonSavedState
        super.onRestoreInstanceState(restoredState.superState)
        update(restoredState.restore())
    }

}

interface UpdateChoiceButton : UpdateText {

    fun update(state: ChoiceUiState)
    
    fun update(
         color: String,
         clickable: Boolean,
         enabled: Boolean
    )
}