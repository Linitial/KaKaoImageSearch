package com.linitial.kakaoimagesearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.linitial.kakaoimagesearch.data.imageSearch.repository.ImageInfo
import com.linitial.kakaoimagesearch.data.imageSearch.repository.ImageSearchRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val imageSearchRepository: ImageSearchRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _helloWorld = MutableLiveData<String>()
    val helloWorld: LiveData<String> = _helloWorld

    private val _imageListData = MutableLiveData<PagingData<ImageInfo>>()
    val imageListData: LiveData<PagingData<ImageInfo>> = _imageListData


    fun searchImage(query: String) {
        imageSearchRepository.searchImage(keyWord = query)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    _imageListData.value = it
                },
                onError = {

                },
                onComplete = {

                }
            ).addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}