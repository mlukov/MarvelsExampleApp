package com.mlukov.marvels.presentation.comic.list.model

import android.accounts.AuthenticatorDescription
import java.util.Date
import java.io.Serializable

open class ComicViewData(val id :Long?,
                         val title: String?,
                         val thumbUlr: String?,
                         val description: String? ): Serializable {
}
