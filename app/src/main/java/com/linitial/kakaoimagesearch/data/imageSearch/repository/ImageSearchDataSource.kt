package com.linitial.kakaoimagesearch.data.imageSearch.repository

import com.linitial.kakaoimagesearch.data.imageSearch.repository.source.network.response.ImageInfoDto
import io.reactivex.rxjava3.core.Single

interface ImageSearchDataSource {

    fun getSearchImage(keyWord: String, sort: String, page: Int, size: Int): Single<ImageInfoDto>

}




