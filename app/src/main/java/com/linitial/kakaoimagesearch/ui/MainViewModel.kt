package com.linitial.kakaoimagesearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.linitial.kakaoimagesearch.data.imageSearch.repository.reponse.ImageInfo
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

    private val _imageListData = MutableLiveData<PagingData<ImageInfo>>()
    val imageListData: LiveData<PagingData<ImageInfo>> = _imageListData

    private val _hideKeyboard = MutableLiveData<Unit>()
    val hideKeyboard = _hideKeyboard

    private val _emptyResult = MutableLiveData<Boolean>()
    val emptyResult = _emptyResult

    private val _loadingStatus = MutableLiveData<Boolean>()
    val loadingStatus = _loadingStatus

    fun setEmptyList() {
        _imageListData.value = PagingData.empty()
    }

    fun searchImage(query: String) {
        _hideKeyboard.value = Unit
        _emptyResult.value = false
        _loadingStatus.value = true

        imageSearchRepository.searchImage(keyWord = query, "", 1, 10)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    _loadingStatus.value = false
                    _emptyResult.value = false
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

    fun setEmptyResult() {
        _emptyResult.value = true
    }
}