package com.linitial.kakaoimagesearch.util

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

class ResourceManager(
    private val context: Context
) {

    fun string(@StringRes resId: Int): String = context.getString(resId)

}