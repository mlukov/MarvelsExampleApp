package com.mlukov.marvels.presentation.providers

import android.support.annotation.StringRes

interface IResourceProvider {

    fun getString(@StringRes stringResId: Int): String
}
