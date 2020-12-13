package com.linitial.kakaoimagesearch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.widget.textChanges
import com.linitial.kakaoimagesearch.R
import com.linitial.kakaoimagesearch.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val vm: MainViewModel by viewModel()
    private val adapter = ImageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.also {
            it.vm = vm
        }

        binding.etSearch.textChanges()
            .debounce(2L, TimeUnit.SECONDS)
            .filter { it != null && it.isNotEmpty() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }

        adapter.eventListener = object: ImageAdapter.EventListener {

            override fun onClick(item: ItemImage) {

            }
        }
        vm.imageListData.observe(this, {
            adapter.submitData(lifecycle, it)
        })

    }

    @BindingAdapter(*["vm", "items"], requireAll = true)
    fun setItems(recyclerView: RecyclerView, vm: MainViewModel, items: PagedList<ItemImage>){

    }
}