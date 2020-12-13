package com.linitial.kakaoimagesearch.data.imageSearch.repository

import android.media.Image
import androidx.paging.rxjava2.RxPagingSource
import io.reactivex.Single

class SearchDataSource(
    private val query: String,
    private val imageSearchAPI: ImageSearchAPI
): RxPagingSource<Int, ImageInfo>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ImageInfo>> {
        val currentPage = params.key ?: 1
        val nextKey = currentPage.plus(1)

        return imageSearchAPI.getSearchImage(
            apiKey = "",
            keyWord = query,
            sort = "",
            page = currentPage,
            size = params.loadSize
        )
            .map {
                LoadResult.Page(
                    it.imageInfoList ?: listOf(),
                    if(currentPage == 1) null else currentPage - 1,
                    if(it.metaData?.isEnd == true) null else currentPage + 1
                ) as LoadResult<Int, ImageInfo>
            }
            .onErrorReturn { LoadResult.Error(it) }
    }

}