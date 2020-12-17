package com.linitial.kakaoimagesearch.util

import android.content.Context
import androidx.annotation.StringRes
class ResourceManager(
    private val context: Context
) {

    fun string(@StringRes resId: Int): String = context.getString(resId)
}
