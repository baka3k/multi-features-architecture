package com.baka3k.core.common.logger

import android.util.Log
import com.baka3k.core.common.BuildConfig

object Logger {
    private const val TAG = "HAHA"

    fun d(message: String, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message, throwable)
        }
    }

    fun w(message: String, throwable: Throwable? = null) {
        Log.w(TAG, message, throwable)
    }

    fun e(message: String, throwable: Throwable? = null) {
        Log.e(TAG, message, throwable)
    }

    const val VERBOSE = 2
    const val DEBUG = 3
    const val INFO = 4
    const val WARN = 5
    const val ERROR = 6
    const val ASSERT = 7
}