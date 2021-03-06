package com.mlukov.marvels.presentation.comic.details.view


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

import com.mlukov.marvels.R
import com.mlukov.marvels.presentation.comic.details.model.ComicDetailsViewData
import com.mlukov.marvels.presentation.comic.details.presenter.IComicDetailsPresenter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import kotlin.jvm.java

class ComicDetailsFragment : Fragment(), IComicDetailsView {

    companion object {

        val TAG = ComicDetailsFragment::class.java.simpleName

        val COMIC_DETAILS_KEY = "ComicDetailsFragment.comicDetailsViewData"

        fun newInstance(comicDetailsViewData: ComicDetailsViewData): ComicDetailsFragment{

            val bundle = Bundle()
            bundle.putSerializable(COMIC_DETAILS_KEY, comicDetailsViewData)
            val fragment =  ComicDetailsFragment()
            fragment.setArguments(bundle)
            return fragment;
        }
    }

    private var listSwipeRefreshLayout: SwipeRefreshLayout? = null

    private var titleTextView: AppCompatTextView? = null
    private var thumbnailImageView: AppCompatImageView? = null
    private var descriptionTextView: AppCompatTextView? = null

    private var comicDetailsViewData : ComicDetailsViewData? = null

    @Inject
    protected lateinit var detailsPresenter: IComicDetailsPresenter

    //region constructor
    fun ComicDetailsFragment() {
        // Required empty public constructor
    }
    //endregion

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

        val arguments = savedInstanceState ?: getArguments()
        comicDetailsViewData = arguments?.getSerializable(COMIC_DETAILS_KEY ) as ComicDetailsViewData
    }

    override fun onAttach(context: Context) {

        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
                              savedInstanceState : Bundle?) : View? {

        val view = inflater.inflate(R.layout.fragment_comic_details, container, false)

        listSwipeRefreshLayout  = view.findViewById( R.id.swipeToRefreshLayout )
        titleTextView           = view.findViewById( R.id.titleTextView )
        thumbnailImageView      = view.findViewById( R.id.thumbnailImageView )
        descriptionTextView     = view.findViewById( R.id.descriptionTextView )

        listSwipeRefreshLayout?.setColorSchemeResources(android.R.color.holo_orange_dark)
        listSwipeRefreshLayout?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {

            detailsPresenter.loadComicDetails(true, comicDetailsViewData?.id ?: 0L )
        })

        return view
    }

    override fun onResume() {

        super.onResume()
        setData()

        detailsPresenter.loadComicDetails(false, comicDetailsViewData?.id ?: 0 )

    }

    override fun onPause() {

        super.onPause()
        detailsPresenter.dispose()
    }

    override fun onSaveInstanceState(outState: Bundle) {

        outState.putSerializable(COMIC_DETAILS_KEY, comicDetailsViewData)
        super.onSaveInstanceState(outState)
    }

    override fun onLoadingStateChange(isLoading: Boolean) {

        listSwipeRefreshLayout?.setRefreshing(isLoading)
    }

    override fun onDetailsLoaded(comicDetailsViewData: ComicDetailsViewData) {

        this.comicDetailsViewData = comicDetailsViewData
        setData()
    }

    override fun onError(errorMessage: String) {

        Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun setData(){

        titleTextView?.text = comicDetailsViewData?.title
        descriptionTextView?.text = comicDetailsViewData?.description

        if( thumbnailImageView != null && comicDetailsViewData != null ){

            Glide.with(this)
                    .load(comicDetailsViewData?.thumbUlr)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(thumbnailImageView)
        }

    }

    //endregion IComicListView implementation

}
