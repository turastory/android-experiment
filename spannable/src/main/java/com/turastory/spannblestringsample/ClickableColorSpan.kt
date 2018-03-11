package com.turastory.spannblestringsample

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

/**
 * Created by soldi on 2018-03-12.
 */

abstract class ClickableColorSpan(private val defaultColor: Int, private val activeColor: Int) : ClickableSpan() {

    private var isClickable: Boolean = false
    private var isActive: Boolean = false

    init {
        setClickable(true)
    }

    fun setClickable(clickable: Boolean) {
        isClickable = clickable
        setActive(false)
    }

    fun setActive(active: Boolean) {
        isActive = active
    }

    override fun onClick(widget: View) {
        if (isClickable) {
            onClicked(widget)
        }
    }

    abstract fun onClicked(widget: View)

    override fun updateDrawState(ds: TextPaint) {
        ds.color = currentStateColor
        ds.isUnderlineText = true
    }

    private val currentStateColor: Int
        get() = if (isActive) activeColor else defaultColor
}
