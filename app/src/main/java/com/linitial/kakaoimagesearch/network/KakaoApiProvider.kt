package com.linitial.kakaoimagesearch.network

import com.google.gson.GsonBuilder
import com.linitial.kakaoimagesearch.BuildConfig
import com.linitial.kakaoimagesearch.data.imageSearch.repository.ImageSearchAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface KakaoApiProvider {
    companion object {
        const val REST_API_KEY = "e803017eaa0d53415ceeb62d42667f65"
        const val AUTH_HEADER = "KakaoAK $REST_API_KEY"
        const val KAKAO_OPEN_API_URL = "https://dapi.kakao.com"
        const val CONNECT_TIME_OUT = 10000L
        const val READ_TIME_OUT = 10000L
        const val WRITE_TIME_OUT = 10000L
    }

    val imageSearchAPI: ImageSearchAPI
}

class KakaoApiProviderImpl: KakaoApiProvider {

    private val gson by lazy {
        GsonBuilder()
            .setLenient()
            .create()
    }

    private val client by lazy {
        OkHttpClient.Builder()
            .connectTimeout(KakaoApiProvider.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
            .readTimeout(KakaoApiProvider.READ_TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(KakaoApiProvider.WRITE_TIME_OUT, TimeUnit.MILLISECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                })
            .build()
    }

    private val builder by lazy {
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(EnumConverterFactory())
    }

    fun build(baseUrl: String): Retrofit {
        return builder.run {
            baseUrl(baseUrl)
            client(client)
            build()
        }
    }

    private val kakao by lazy { build(KakaoApiProvider.KAKAO_OPEN_API_URL) }
    override val imageSearchAPI: ImageSearchAPI by lazy { kakao.create(ImageSearchAPI::class.java) }
}