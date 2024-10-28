package com.usatayamish.expertcoursequizgame.views.stats


import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import com.usatayamish.expertcoursequizgame.R
import java.io.Serializable

class StatsTextView : androidx.appcompat.widget.AppCompatTextView, UpdateStats {

    private lateinit var state: StatsUiState

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = StatsSavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as StatsSavedState
        super.onRestoreInstanceState(restoredState.superState)
        update(restoredState.restore())
    }

    override fun update(uiState: StatsUiState) {
        state = uiState
        state.update(this)
    }

    override fun update(corrects: Int, incorrects: Int) {
        text = resources.getString(R.string.stats, corrects, incorrects)
    }
}

interface StatsUiState: Serializable{
    fun update(statsTextView: UpdateStats)

    class Base(
        private val corrects: Int,
        private val incorrects: Int
    ): StatsUiState {

        override fun update(statsTextView: UpdateStats) {
            statsTextView.update(corrects, incorrects)
        }

    }
}

interface UpdateStats {

    fun update(uiState: StatsUiState)

    fun update(corrects: Int, incorrects: Int)
}