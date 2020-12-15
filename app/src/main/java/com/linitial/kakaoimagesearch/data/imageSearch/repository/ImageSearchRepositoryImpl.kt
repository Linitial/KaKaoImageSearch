package com.linitial.kakaoimagesearch.data.imageSearch.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.linitial.kakaoimagesearch.network.KakaoApiProvider
import io.reactivex.Flowable

class ImageSearchRepositoryImpl(
    private val kakaoApiProvider: KakaoApiProvider
): ImageSearchRepository {

    override fun searchImage(keyWord: String, sort: String, page: Int, size: Int): Flowable<PagingData<ImageInfo>> {
        val config = PagingConfig(pageSize = 10, initialLoadSize = 10)

        return Pager<Int, ImageInfo>(config) {
            SearchDataSource(keyWord, kakaoApiProvider.imageSearchAPI)
        }.flowable
    }
}