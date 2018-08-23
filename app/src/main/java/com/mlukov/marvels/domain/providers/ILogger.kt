package com.mlukov.marvels.domain.providers

interface ILogger {

    fun e( tag:String?, message: String?, throwable: Throwable? )
}