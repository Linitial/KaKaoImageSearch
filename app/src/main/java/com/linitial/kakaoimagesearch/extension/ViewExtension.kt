package com.linitial.kakaoimagesearch.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.hideKeyboard() {
    this.windowToken?.let {
        this.clearFocus()
        val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(it, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}