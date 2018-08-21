package com.mlukov.marvels.domain.calculators

interface IHashCalculator {

    fun calculate(vararg args: String): String
}