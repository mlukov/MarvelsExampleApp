package com.mlukov.marvels.presentation.providers

import android.content.Context

class ResourceProvider(private val context: Context?) : IResourceProvider {


    override fun getString(stringResId: Int): String {

        return context?.getString(stringResId) ?: ""
    }
}
