package com.linitial.kakaoimagesearch.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding4.widget.textChanges
import com.linitial.kakaoimagesearch.config.AppConstants
import com.linitial.kakaoimagesearch.data.imageSearch.repository.reponse.ImageInfo
import com.linitial.kakaoimagesearch.extension.hideKeyboard
import com.linitial.kakaoimagesearch.databinding.ActivityMainBinding
import com.linitial.kakaoimagesearch.extension.gone
import com.linitial.kakaoimagesearch.extension.visible
import com.linitial.kakaoimagesearch.ui.detail.DetailActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageAdapter: SearchImageAdapter

    private val vm: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this)).apply {
            setContentView(root)
        }

        binding.etSearch.textChanges()
            .doOnNext { vm.setEmptyList() }
            .debounce(1L, TimeUnit.SECONDS)
            .filter { it != null && it.isNotBlank() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { vm.searchImage(it.toString()) }

        imageAdapter = SearchImageAdapter(
            clickListener = { showDetail(it) },
            emptyResultListener = { vm.setEmptyResult() }
        )

        binding.rvSearch.run {
            adapter = imageAdapter.withLoadStateFooter(SearchStateAdapter { imageAdapter.retry() })
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@MainActivity, AppConstants.GRID_SPAN_SIZE).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return imageAdapter.getItemViewType(position)
                    }
                }
            }
        }
    }

    private fun initData() {
        vm.imageListData.observe(this) {
            if (::imageAdapter.isInitialized) {
                imageAdapter.submitData(lifecycle, it)
            }
        }

        vm.hideKeyboard.observe(this) {
            binding.etSearch.hideKeyboard()
        }

        vm.emptyResult.observe(this) {
            if(it) {
                binding.tvEmptyResult.visible()
            }else {
                binding.tvEmptyResult.gone()
            }
        }

        vm.loadingStatus.observe(this) {
            if(it) {
                binding.pbLoading.visible()
            }else {
                binding.pbLoading.gone()
            }
        }

        vm.toast.observe(this) {
            showToast(it)
        }
    }

    private fun showDetail(item: ImageInfo) {
        val intent = Intent(this, DetailActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            .putExtra(DetailActivity.EXTRA_DETAIL_ITEM, item)

        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}