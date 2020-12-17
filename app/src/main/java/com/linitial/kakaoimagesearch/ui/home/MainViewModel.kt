package com.linitial.kakaoimagesearch.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.linitial.kakaoimagesearch.R
import com.linitial.kakaoimagesearch.data.imageSearch.repository.reponse.ImageInfo
import com.linitial.kakaoimagesearch.data.imageSearch.repository.ImageSearchRepository
import com.linitial.kakaoimagesearch.extension.isNetworkError
import com.linitial.kakaoimagesearch.util.DeviceManager
import com.linitial.kakaoimagesearch.util.ResourceManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import timber.log.Timber

class MainViewModel(
    private val deviceManager: DeviceManager,
    private val resourceManager: ResourceManager,
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

    private val _toast = MutableLiveData<String>()
    val toast: LiveData<String> = _toast

    fun setEmptyList() {
        _imageListData.value = PagingData.empty()
    }

    fun setEmptyResult() {
        _emptyResult.value = true
    }

    fun searchImage(keyword: String) {
        if (!deviceManager.isNetworkEnable()) {
            _toast.value = resourceManager.string(R.string.error_network_disconnect)
            return
        }

        _hideKeyboard.value = Unit
        _emptyResult.value = false
        _loadingStatus.value = true

        imageSearchRepository.searchImage(query = keyword)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    _loadingStatus.value = false
                    _emptyResult.value = false
                    _imageListData.value = it
                },
                onError = {
                    Timber.e(it)

                    _toast.value = if (isNetworkError(it)) {
                        if (deviceManager.isNetworkEnable()) {
                            resourceManager.string(R.string.error_search_result)
                        } else {
                            resourceManager.string(R.string.error_network_disconnect)
                        }
                    } else {
                        resourceManager.string(R.string.error_search_result)
                    }
                },
                onComplete = {}
            ).addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}