package com.mlukov.marvels.presentation.base

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.transition.TransitionSet
import android.view.MenuItem
import android.view.View

import dagger.android.AndroidInjection

import com.mlukov.marvels.R

open class BaseActivity : AppCompatActivity() {


    //region show screen
    fun showScreen(content : Fragment,
                   contentTag : String,
                   addToBackStack : Boolean,
                   transitionContent : Boolean) {

        val ft = getSupportFragmentManager().beginTransaction()

        // Content area slide animation
        if (transitionContent) {

            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        }

        ft.replace( R.id.placeholder_content, content, contentTag )

        if (addToBackStack) {
            ft.addToBackStack(contentTag + System.identityHashCode(content))
        }

        ft.commitAllowingStateLoss()
    }

    fun showScreenWithSharedTransition(destination: Fragment,
                                       sharedElements: List<View>?,
                                       sharedElementTransitionNames: List<String>?,
                                       sharedElementEnterAnimation: TransitionSet,
                                       sharedElementReturnAnimation: TransitionSet,
                                       contentTag: String,
                                       addToBackStack: Boolean) {


        val fm = supportFragmentManager
        val ft = fm.beginTransaction()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                && sharedElements != null
                && sharedElements.size > 0
                && sharedElementTransitionNames != null
                && sharedElementTransitionNames.size > 0
                && sharedElements.size == sharedElementTransitionNames.size) {

            ft.setTransition(FragmentTransaction.TRANSIT_NONE)

            for (i in sharedElements.indices) {

                if (sharedElements[i] == null || TextUtils.isEmpty(sharedElementTransitionNames[i]))
                    continue

                ft.addSharedElement(sharedElements[i], sharedElementTransitionNames[i])
            }

            destination.sharedElementEnterTransition = sharedElementEnterAnimation
            destination.sharedElementReturnTransition = sharedElementReturnAnimation
        }

        ft.replace(R.id.placeholder_content, destination, contentTag)

        if (addToBackStack) {
            ft.addToBackStack(contentTag + System.identityHashCode(destination).toString())
        }

        ft.commitAllowingStateLoss()
        fm.executePendingTransactions()
    }
    //endregion

    //region AppCompatActivity implementation
    override fun onCreate(savedInstanceState : Bundle?) {

        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        when(id) {

            android.R.id.home -> {

                if ( getSupportFragmentManager().getBackStackEntryCount() > 0) {

                    getSupportFragmentManager().popBackStack()

                    return true
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
    //endregion
}