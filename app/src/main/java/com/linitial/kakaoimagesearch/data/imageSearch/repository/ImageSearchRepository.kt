package com.linitial.kakaoimagesearch.data.imageSearch.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.linitial.kakaoimagesearch.config.AppConstants
import com.linitial.kakaoimagesearch.data.imageSearch.repository.reponse.ImageInfo
import com.linitial.kakaoimagesearch.data.imageSearch.repository.source.ImageSearchDataSource
import com.linitial.kakaoimagesearch.network.KakaoApiProvider
import io.reactivex.Flowable

interface ImageSearchRepository {

    fun searchImage(
        query: String,
        sort: SortType = SortType.RECENCY,
        page: Int = AppConstants.DEFAULT_PER_PAGE_SIZE,
        size: Int = AppConstants.DEFAULT_PER_PAGE_SIZE
    ): Flowable<PagingData<ImageInfo>>
}

class ImageSearchRepositoryImpl(
    private val kakaoApiProvider: KakaoApiProvider
): ImageSearchRepository {

    override fun searchImage(query: String, sort: SortType, page: Int, size: Int): Flowable<PagingData<ImageInfo>> {
        val config = PagingConfig(pageSize = page, initialLoadSize = size)
        val searchDataSource = ImageSearchDataSource(
            imageSearchAPI = kakaoApiProvider.imageSearchAPI,
            query = query,
            sort = sort
        )

        return Pager<Int, ImageInfo>(config) { searchDataSource }.flowable
    }
}