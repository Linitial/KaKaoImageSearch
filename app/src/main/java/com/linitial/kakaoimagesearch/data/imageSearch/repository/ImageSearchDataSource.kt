package com.linitial.kakaoimagesearch.data.imageSearch.repository

import androidx.paging.rxjava2.RxPagingSource
import com.linitial.kakaoimagesearch.config.AppConstants
import com.linitial.kakaoimagesearch.data.imageSearch.repository.reponse.ImageInfo
import com.linitial.kakaoimagesearch.network.KakaoApiProvider
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ImageSearchDataSource(
    private val query: String,
    private val imageSearchAPI: ImageSearchAPI
): RxPagingSource<Int, ImageInfo>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ImageInfo>> {
        val currentPage = params.key ?: 1

        return imageSearchAPI.getSearchImage(
            apiKey = "KakaoAK ${KakaoApiProvider.REST_API_KEY}",
            keyWord = query,
            sort = "",
            page = currentPage,
            size = params.loadSize
        )
            .subscribeOn(Schedulers.computation())
            .map { response ->
                response.metaData?.let { metaData ->
                    if(metaData.totalCount == 0){
                        response.imageInfoList?.clear()
                        response.imageInfoList?.add(ImageInfo(thumbNailUrl = AppConstants.EMPTY_RESULT, "", "", null))
                    }
                }

                LoadResult.Page(
                    data = response.imageInfoList ?: listOf(ImageInfo(thumbNailUrl = AppConstants.EMPTY_RESULT, "", "", null)),
                    prevKey = if(currentPage == 1) null else currentPage - 1,
                    nextKey = if(response.metaData?.isEnd == true) null else currentPage + 1
                ) as LoadResult<Int, ImageInfo>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }
}