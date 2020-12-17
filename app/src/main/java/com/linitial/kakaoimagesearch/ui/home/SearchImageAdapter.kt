package com.linitial.kakaoimagesearch.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.linitial.kakaoimagesearch.config.PAGING
import com.linitial.kakaoimagesearch.data.imageSearch.repository.reponse.ImageInfo
import com.linitial.kakaoimagesearch.databinding.ItemGridImageBinding

class SearchImageAdapter(
    private val clickListener: (item: ImageInfo) -> Unit,
    private val emptyResultListener: () -> Unit
) : PagingDataAdapter<ImageInfo, ImageViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemGridImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position)?.let {
            if (it.thumbNailUrl == PAGING.EMPTY_RESULT) {
                emptyResultListener.invoke()
            } else {
                holder.bindData(it, clickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) {
            PAGING.GRID_SPAN_SIZE
        } else {
            PAGING.LOAD_SPAN_SIZE
        }
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ImageInfo>() {

    override fun areItemsTheSame(oldItem: ImageInfo, newItem: ImageInfo): Boolean {
        return oldItem.thumbNailUrl == newItem.thumbNailUrl
    }

    override fun areContentsTheSame(oldItem: ImageInfo, newItem: ImageInfo): Boolean {
        return oldItem == newItem
    }
}

class ImageViewHolder(
    private val view: ItemGridImageBinding,
) : RecyclerView.ViewHolder(view.root) {

    fun bindData(item: ImageInfo, clickListener: (item: ImageInfo) -> Unit) {
        item.thumbNailUrl?.let {
            Glide.with(view.ivGirdImage)
                .load(it)
                .fitCenter()
                .into(view.ivGirdImage)
        }

        view.root.setOnClickListener {
            clickListener(item)
        }
    }
}
