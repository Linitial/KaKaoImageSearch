package com.linitial.kakaoimagesearch.data.imageSearch.repository

import com.linitial.kakaoimagesearch.data.imageSearch.repository.source.network.response.ImageInfoDto
import io.reactivex.rxjava3.core.Single

class ImageSearchNetworkDataSource(
    private val imageSearchAPI: ImageSearchAPI
): ImageSearchDataSource {

    override fun getSearchImage(
        keyWord: String,
        sort: String,
        page: Int,
        size: Int
    ): Single<ImageInfoDto> {
        return imageSearchAPI.getSearchImage(
            apiKey = = "KakaoAK ${KakaoApiProvider.REST_API_KEY}",
            keyWord = keyWord,
            sort = sort,
            page = page,
            size = size
        )
    }
}