package com.mlukov.marvels.utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

class HashCalculator {

    fun calculate(vararg args: String): String? {

        val input = concatenateArgs(args.toList())
        val digest = generateDigest(input)
        return toHexString(digest)
    }

    private fun concatenateArgs(args: List<String>): String {

        val stringBuilder = StringBuilder()
        for (arg in args) {
            stringBuilder.append(arg)
        }
        return stringBuilder.toString()
    }

    /**
     * Generates MD5 digest from an input string.
     *
     * @param input - input string
     * @return a byte array of the MD5 digest
     */
    private fun generateDigest(input: String): ByteArray? {
        try {
            val md = MessageDigest.getInstance("MD5")
            md.update(input.toByteArray())

            return md.digest()
        } catch (nsae: NoSuchAlgorithmException) {
            nsae.printStackTrace()
        }

        return null
    }

    private fun toHexString(digest: ByteArray?): String? {

        if (digest == null)
            return null

        val stringBuilder = StringBuilder()

        for (digestByte: Byte in digest) {
            stringBuilder.append( Integer.toString((digestByte and 0xFF.toByte() ) + 0x100 , 16).substring(1))
        }

        return stringBuilder.toString()
    }
}