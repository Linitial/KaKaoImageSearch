package com.linitial.kakaoimagesearch.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding4.widget.textChanges
import com.linitial.kakaoimagesearch.config.AppConstants
import com.linitial.kakaoimagesearch.extension.hideKeyboard
import com.linitial.kakaoimagesearch.databinding.ActivityMainBinding
import com.linitial.kakaoimagesearch.extension.gone
import com.linitial.kakaoimagesearch.extension.visible
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageAdapter: SearchImageAdapter

    private val vm: MainViewModel by viewModel()

    /**
     * 1. 에러 핸들링 하기
     * - 네트워크 미연결
     * - http error 처리. 예) 500에 대한 처리
     * 
     * 2. 이미지 상세화면 구현하기.
     * 3. string.xml 에 텍스트 리소스로 분리.
     */
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
            .subscribe {
                vm.searchImage(it.toString())
            }

        imageAdapter = SearchImageAdapter(
            clickListener = {
                Toast.makeText(this@MainActivity, it.toString(), Toast.LENGTH_SHORT).show()
            },
            emptyResultListener = {
                vm.setEmptyResult()
            }
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
    }
}