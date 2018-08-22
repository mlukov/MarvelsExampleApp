package com.mlukov.marvels.utils

import android.os.Build
import android.view.View

class ViewUtils {

    fun setTransitionName(sharedView: View?, transitionName: String) {

        if ( sharedView != null) {

            sharedView.transitionName = transitionName
        }
    }
}