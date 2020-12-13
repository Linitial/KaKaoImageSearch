package com.linitial.kakaoimagesearch.network

import com.linitial.kakaoimagesearch.data.imageSearch.repository.ImageSearchAPI

class KakaoApiProvider: RetrofitCreator() {

    companion object {
        const val REST_API_KEY = "e803017eaa0d53415ceeb62d42667f65"
        const val KaKaoOpenAPI = "https://dapi.kakao.com"
    }

    private val kakao = build(KaKaoOpenAPI)

    val imageSearchAPI: ImageSearchAPI by lazy { kakao.create(ImageSearchAPI::class.java) }
}