package com.mlukov.marvels.presentation.comic.list.view


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import com.mlukov.marvels.R
import com.mlukov.marvels.presentation.comic.list.model.ComicListViewModel
import com.mlukov.marvels.presentation.comic.list.presenter.IComicListPresenter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ComicListFragment : Fragment(), IComicListView {

    companion object {

        val TAG = ComicListFragment::class.java.simpleName

        fun newInstance(): ComicListFragment{
            return ComicListFragment()
        }
    }

    private var listRecyclerView: RecyclerView? = null
    private var listSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var emptyListText: TextView? = null

    @Inject
    protected lateinit var listPresenter: IComicListPresenter

    @Inject
    protected lateinit var listAdapter :ComicsAdapter

    override fun onAttach(context: Context) {

        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
                              savedInstanceState : Bundle?) : View? {

        val view = inflater.inflate(R.layout.fragment_comic_list, container, false)

        listRecyclerView = view.findViewById(R.id.listRecyclerView)
        listSwipeRefreshLayout = view.findViewById(R.id.listSwipeRefreshLayout)
        emptyListText = view.findViewById(R.id.emptyListText)

        listRecyclerView?.setLayoutManager(LinearLayoutManager(activity, RecyclerView.VERTICAL, false))
        listRecyclerView?.setAdapter(listAdapter)

        emptyListText?.setVisibility(View.GONE)

        listSwipeRefreshLayout?.setColorSchemeResources(android.R.color.holo_orange_dark)
        listSwipeRefreshLayout?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {

            listPresenter.loadComics(true)
        })

        return view
    }

    override fun onResume() {

        super.onResume()
        listPresenter.loadComics(false )
    }

    override fun onPause() {

        super.onPause()
        listPresenter.dispose()
    }

    //region IComicListView implementation
    override fun onLoadingStateChange(isLoading: Boolean) {

        listSwipeRefreshLayout?.setRefreshing(isLoading)
    }

    override fun onComicsLoaded(comicListViewModel: ComicListViewModel) {

        listAdapter.updateList(comicListViewModel.list )

        val listIsEmpty = comicListViewModel.list.size == 0
        emptyListText?.setVisibility(if (listIsEmpty) View.VISIBLE else View.GONE)
    }

    override fun onError(errorMessage: String) {

        listAdapter.notifyDataSetChanged()

        emptyListText?.setVisibility( if( listAdapter.itemCount == 0 ) View.VISIBLE else View.INVISIBLE )

        Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
    }
    //endregion IComicListView implementation
}
