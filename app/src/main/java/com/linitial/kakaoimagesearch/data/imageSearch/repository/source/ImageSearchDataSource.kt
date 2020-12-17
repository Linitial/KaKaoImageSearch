package com.linitial.kakaoimagesearch.data.imageSearch.repository.source

import androidx.paging.rxjava2.RxPagingSource
import com.linitial.kakaoimagesearch.config.PAGING
import com.linitial.kakaoimagesearch.data.imageSearch.repository.ImageSearchAPI
import com.linitial.kakaoimagesearch.data.imageSearch.repository.SortType
import com.linitial.kakaoimagesearch.data.imageSearch.repository.reponse.ImageInfo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ImageSearchDataSource(
    private val imageSearchAPI: ImageSearchAPI,
    private val query: String,
    private val sort: SortType
) : RxPagingSource<Int, ImageInfo>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ImageInfo>> {
        val currentPage = params.key ?: 1

        return imageSearchAPI.getSearchImage(
            keyWord = query,
            sort = sort,
            page = currentPage,
            size = params.loadSize
        )
            .subscribeOn(Schedulers.computation())
            .map { response ->
                response.metaData?.let { metaData ->
                    if (metaData.totalCount == 0) {
                        response.imageInfoList?.clear()
                        response.imageInfoList?.add(ImageInfo(PAGING.EMPTY_RESULT, "", "", null))
                    }
                }

                LoadResult.Page(
                    data = response.imageInfoList ?: listOf(
                        ImageInfo(
                            PAGING.EMPTY_RESULT,
                            "",
                            "",
                            null
                        )
                    ),
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (response.metaData?.isEnd == true) null else currentPage + 1
                ) as LoadResult<Int, ImageInfo>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }
}