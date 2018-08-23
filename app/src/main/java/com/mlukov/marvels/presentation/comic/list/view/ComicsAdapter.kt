package com.mlukov.marvels.presentation.comic.list.view

import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

import com.mlukov.marvels.R
import com.mlukov.marvels.presentation.comic.details.model.ComicDetailsViewData
import com.mlukov.marvels.presentation.comic.list.model.ComicViewData
import com.mlukov.marvels.presentation.main.IMainNavigator
import javax.inject.Inject


class ComicsAdapter
@Inject
constructor( val mainNavigator: IMainNavigator) : RecyclerView.Adapter<ComicsAdapter.ComicViewHolder>() {

    val list = mutableListOf<ComicViewData>()

    class ComicViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        internal val titleTextView: AppCompatTextView? = itemView?.findViewById(R.id.titleTextView)
        internal val comicImageView: AppCompatImageView? = itemView?.findViewById(R.id.comicImageView)

        internal fun bind(comic: ComicViewData?) {

            if (comic == null)
                return

            titleTextView?.text = comic.title
            Glide.with(this.itemView)
                    .load(comic.thumbUlr)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(comicImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.adapter_list_item, parent, false)
        val viewHodler = ComicViewHolder(view)

        viewHodler.itemView.setOnClickListener {

            val position = viewHodler.getAdapterPosition()
            if (position != RecyclerView.NO_POSITION) {

                val comic = list.get(position)
                mainNavigator.showComicDetails(ComicDetailsViewData(comic))
            }
        }

        return viewHodler
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {

        if (list.size <= position)
            holder.bind(null)
        else {

            holder.bind(list[position])
        }
    }

    fun updateList(comicViewDataList: List<ComicViewData>?) {

        list.clear()
        if (comicViewDataList?.isEmpty() == false) {

            list.addAll(comicViewDataList)
        }
        notifyDataSetChanged()
    }

    companion object {
         val TAG = ComicsAdapter::class.simpleName
    }
}
