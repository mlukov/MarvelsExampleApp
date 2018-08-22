package com.mlukov.marvels.presentation.comic.list.view

import android.support.annotation.StringRes
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

import com.mlukov.marvels.R
import com.mlukov.marvels.presentation.comic.details.model.ComicDetailsViewData
import com.mlukov.marvels.presentation.comic.list.model.ComicViewData
import com.mlukov.marvels.presentation.main.IMainNavigator
import com.mlukov.marvels.presentation.providers.IResourceProvider
import com.mlukov.marvels.utils.ViewUtils
import javax.inject.Inject



class ComicsAdapter

@Inject
constructor( val mainNavigator: IMainNavigator, val resourceProvider: IResourceProvider) : RecyclerView.Adapter<ComicsAdapter.ComicViewHolder>() {

    val list = mutableListOf<ComicViewData>()

    class ComicViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        internal val titleTextView: AppCompatTextView? = itemView?.findViewById(R.id.titleTextView)
        internal val comicImageView: AppCompatImageView? = itemView?.findViewById(R.id.comicImageView)

        internal fun bind(comic: ComicViewData?) {

            if( comic == null )
                return

            titleTextView?.text = comic.title
            Glide.with(this.itemView)
                    .load( comic.thumbUlr)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(comicImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.adapter_list_item, parent, false)
        val viewHodler = ComicViewHolder(view)

        viewHodler.itemView.setOnClickListener{

            val position = viewHodler.getAdapterPosition()
            if (position != RecyclerView.NO_POSITION) {

                val comic = list.get(position)
                val sharedViews = listOf(viewHodler.comicImageView, viewHodler.titleTextView)
                setSharedElementTransitionName( viewHodler.comicImageView!!, R.string.source_shared_element_transition_comic_thumb_image )
                setSharedElementTransitionName( viewHodler.titleTextView!!, R.string.source_shared_element_transition_comic_title_text )
                

                mainNavigator.showComicDetails(ComicDetailsViewData(comic), sharedViews )
            }
        }

        return viewHodler
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {

        if ( list.size <= position )
            holder.bind(null)
        else
            holder.bind(list[position])
    }

    fun updateList( comicViewDataList: List<ComicViewData>?) {

        list.clear()
        if ( comicViewDataList?.isEmpty() == false ) {

            list.addAll(comicViewDataList)
        }
        notifyDataSetChanged()
    }

    private fun removeSharedElementTransitionName(sharedElement: View) {

        ViewUtils().setTransitionName(sharedElement, "" )
    }

    fun setSharedElementTransitionName(sharedElement: View, @StringRes resId: Int ) {

        ViewUtils().setTransitionName(sharedElement, sharedElement.context.getString(resId))
    }

    fun clearTransitionNameOfSharedElements() {

        try {

            if (mRecyclerViewRef != null && mRecyclerViewRef.get() != null) {

                val count = mRecyclerViewRef.get().getChildCount()
                for (index in 0 until count) {

                    val child = mRecyclerViewRef.get().getChildAt(index)

                    val viewHolder = mRecyclerViewRef.get().getChildViewHolder(child)
                    if ( viewHolder is ComicViewHolder ) {

                        if ((viewHolder as ComicViewHolder).titleTextView != null)
                            removeSharedElementTransitionName((viewHolder as ComicViewHolder).titleTextView!!)

                        if ((viewHolder as ComicViewHolder).titleTextView != null)
                            removeSharedElementTransitionName((viewHolder as ComicViewHolder).comicImageView!!)
                    }
                }
            }
        } catch (ex: Exception) {

            Log.e(TAG, ex.message, ex)
        }

    }

    companion object {
         val TAG = ComicsAdapter::class.simpleName
    }
}
