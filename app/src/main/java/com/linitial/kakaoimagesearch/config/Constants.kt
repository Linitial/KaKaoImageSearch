package com.linitial.kakaoimagesearch.config

object API {
    const val REST_API_KEY = "e803017eaa0d53415ceeb62d42667f65"
    const val AUTH_HEADER = "KakaoAK $REST_API_KEY"
    const val KAKAO_OPEN_API_URL = "https://dapi.kakao.com"
}

object PAGING {
    const val EMPTY_RESULT = "EMPTY_RESULT"
    const val GRID_SPAN_SIZE = 3
    const val LOAD_SPAN_SIZE = 1
    const val DEFAULT_PER_PAGE_SIZE = 30
}