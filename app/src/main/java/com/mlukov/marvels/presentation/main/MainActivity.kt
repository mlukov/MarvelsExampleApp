package com.mlukov.marvels.presentation.main

import android.os.Bundle
import android.support.v4.app.Fragment

import com.mlukov.marvels.R
import com.mlukov.marvels.presentation.comic.details.model.ComicDetailsViewData
import com.mlukov.marvels.presentation.comic.details.view.ComicDetailsFragment
import com.mlukov.marvels.presentation.comic.list.view.ComicListFragment
import com.mlukov.marvels.presentation.base.BaseActivity
import com.mlukov.marvels.presentation.providers.IResourceProvider

import javax.inject.Inject

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector

class MainActivity : BaseActivity(), HasSupportFragmentInjector, IMainNavigator {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var resourceProvider: IResourceProvider

    //region AppCompatActivity overrides
    public override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {

        super.onResume()
        showComicList()
    }
    //region AppCompatActivity overrides

    //region HasSupportFragmentInjector implementation
    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {

        return dispatchingAndroidInjector
    }
    //endregion HasSupportFragmentInjector implementation

    //region IMainNavigator implementation
    override fun showComicList() {

        var comicListFragment = supportFragmentManager.findFragmentByTag(ComicListFragment.TAG )
        if (comicListFragment == null) {

            comicListFragment = ComicListFragment.newInstance()
        }

        showScreen(comicListFragment, ComicListFragment.TAG, false, false)
    }

    override fun showComicDetails(comicDetailsViewData: ComicDetailsViewData ) {

        var comicDetailsFragment = supportFragmentManager.findFragmentByTag(ComicDetailsFragment.TAG )
        if (comicDetailsFragment == null) {

            comicDetailsFragment = ComicDetailsFragment.newInstance(comicDetailsViewData )
        }

        showScreen(comicDetailsFragment, ComicListFragment.TAG, true, true)
    }
    //endregion IMainNavigator implementation

}
