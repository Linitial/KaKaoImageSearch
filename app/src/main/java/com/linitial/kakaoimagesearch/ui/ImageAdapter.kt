package com.linitial.kakaoimagesearch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.linitial.kakaoimagesearch.data.imageSearch.repository.ImageInfo
import com.linitial.kakaoimagesearch.databinding.ItemGridImageBinding
import org.joda.time.DateTime

class ImageAdapter: PagingDataAdapter<ImageInfo, ImageViewHolder>(DIFF_CALLBACK) {

    interface EventListener {
        fun onClick(item: ImageInfo)
    }

    var eventListener: EventListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemGridImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding, eventListener)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindData(it)
        }
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ImageInfo>() {

    override fun areItemsTheSame(oldItem: ImageInfo, newItem: ImageInfo): Boolean {
        return oldItem.imageUrl == newItem.imageUrl
    }

    override fun areContentsTheSame(oldItem: ImageInfo, newItem: ImageInfo): Boolean {
        return oldItem == newItem
    }
}

class ImageViewHolder(
    private val view: ItemGridImageBinding,
    private val eventListener: ImageAdapter.EventListener?
) : RecyclerView.ViewHolder(view.root) {

    fun bindData(item: ImageInfo){
        view.root.setOnClickListener {
            eventListener?.onClick(item)
        }
    }

}

data class ItemImage(
    val thumbNailUrl: String,
    val imageUrl: String,
    val displaySiteName: String,
    val dateTime: DateTime
)