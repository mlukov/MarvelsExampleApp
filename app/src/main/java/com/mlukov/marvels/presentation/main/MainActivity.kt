package com.mlukov.marvels.presentation.main

import android.os.Bundle
import android.support.v4.app.Fragment

import com.mlukov.marvels.R
import com.mlukov.marvels.presentation.comic.details.model.ComicDetailsViewData
import com.mlukov.marvels.presentation.comic.details.view.ComicDetailsFragment
import com.mlukov.marvels.presentation.comic.list.view.ComicListFragment
import com.mlukov.marvels.presentation.base.BaseActivity

import javax.inject.Inject

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector

class MainActivity : BaseActivity(), HasSupportFragmentInjector, IMainNavigator {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    //region AppCompatActivity overrides
    public override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {

        super.onResume()
        showComicList()
    }

    override fun onPause() {

        super.onPause()
    }
    //region AppCompatActivity overrides


    //region HasSupportFragmentInjector implementation

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {

        return dispatchingAndroidInjector
    }
    //endregion HasSupportFragmentInjector implementation


    //region IMainNavigator implementation
    override fun showComicList() {

        var articlesFragment = supportFragmentManager.findFragmentByTag(ComicListFragment.TAG )
        if (articlesFragment == null) {

            articlesFragment = ComicListFragment.newInstance()
        }

        showScreen(articlesFragment, ComicListFragment.TAG, false, false)
    }

    override fun showComicDetails(comicDetailsViewData: ComicDetailsViewData) {

        var articleDetailsFragment = supportFragmentManager.findFragmentByTag(ComicDetailsFragment.TAG )
        if (articleDetailsFragment == null) {

            articleDetailsFragment = ComicDetailsFragment.newInstance(comicDetailsViewData )
        }

        showScreen(articleDetailsFragment, ComicDetailsFragment.TAG, true, true )
    }
    //endregion IMainNavigator implementation

}
