package com.mlukov.marvels.domain.providers

class TimestampProvider :ITimestampProvider {

    override fun getTimeStamp(): String {

        return System.currentTimeMillis().toString()
    }
}