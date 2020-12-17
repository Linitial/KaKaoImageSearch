package com.linitial.kakaoimagesearch.data.imageSearch.repository

import com.linitial.kakaoimagesearch.config.API
import com.linitial.kakaoimagesearch.data.imageSearch.repository.reponse.ImageSearchResponse
import com.linitial.kakaoimagesearch.network.KakaoApiProvider
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ImageSearchAPI {

    @GET("/v2/search/image")
    fun getSearchImage(
        @Header("Authorization") apiKey: String = API.AUTH_HEADER,
        @Query("query") keyWord: String,
        @Query("sort") sort: SortType,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Single<ImageSearchResponse>

}