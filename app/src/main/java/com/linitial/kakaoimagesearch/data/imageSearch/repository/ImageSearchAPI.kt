package com.linitial.kakaoimagesearch.data.imageSearch.repository

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ImageSearchAPI {

    @GET("/v2/search/image")
    fun getSearchImage(
        @Header("Authorization") apiKey: String,
        @Query("query") keyWord: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Single<ImageSearchResponse>

}