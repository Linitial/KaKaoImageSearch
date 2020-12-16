package com.linitial.kakaoimagesearch.extension

import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun isNetworkError(throwable: Throwable): Boolean {
    return when (throwable) {
        is SocketTimeoutException -> true
        is UnknownHostException -> true
        else -> false
    }
}