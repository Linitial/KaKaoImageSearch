package com.linitial.kakaoimagesearch.data.imageSearch.repository

import androidx.paging.PagingData
import io.reactivex.Flowable

interface ImageSearchRepository {

    fun searchImage(
        keyWord: String = "",
        sort: String = "accuracy",
        page: Int = 1,
        size: Int = 30
    ): Flowable<PagingData<ImageInfo>>
}
