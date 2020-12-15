package com.linitial.kakaoimagesearch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linitial.kakaoimagesearch.databinding.ItemGridLoadStateBinding
import com.linitial.kakaoimagesearch.extension.gone
import com.linitial.kakaoimagesearch.extension.visible

class SearchStateAdapter(private val retry: () -> Unit): LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(ItemGridLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bindData(loadState, retry)
    }
}

class LoadStateViewHolder(private val view: ItemGridLoadStateBinding) : RecyclerView.ViewHolder(view.root) {
    
    fun bindData(loadState: LoadState, retry: () -> Unit){
        when(loadState){
            is LoadState.Error -> {
                view.tvErrorMsg.text = loadState.error.localizedMessage
                view.tvErrorMsg.visible()
                view.btnRetry.visible()
                view.pbLoading.gone()
            }

            is LoadState.Loading -> {
                view.tvErrorMsg.gone()
                view.btnRetry.gone()
                view.pbLoading.visible()
            }

            is LoadState.NotLoading -> {}
        }

        view.btnRetry.setOnClickListener {
            retry.invoke()
        }
    }

}