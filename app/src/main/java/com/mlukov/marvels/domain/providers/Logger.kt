package com.mlukov.marvels.domain.providers

import android.util.Log

class Logger : ILogger {

    override fun e(tag: String?, message: String?, throwable: Throwable?) {
        Log.e( tag,message, throwable)
    }
}