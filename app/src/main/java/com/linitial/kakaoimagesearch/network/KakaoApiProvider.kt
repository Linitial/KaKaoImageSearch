package com.linitial.kakaoimagesearch.network

import com.google.gson.GsonBuilder
import com.linitial.kakaoimagesearch.BuildConfig
import com.linitial.kakaoimagesearch.config.API
import com.linitial.kakaoimagesearch.data.imageSearch.repository.ImageSearchAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface KakaoApiProvider {
    val imageSearchAPI: ImageSearchAPI
}

class KakaoApiProviderImpl : KakaoApiProvider {

    private val gson by lazy {
        GsonBuilder()
            .setLenient()
            .create()
    }

    private val client by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10000L, TimeUnit.MILLISECONDS)
            .readTimeout(10000L, TimeUnit.MILLISECONDS)
            .writeTimeout(10000L, TimeUnit.MILLISECONDS)
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

    private val kakao by lazy { build(API.KAKAO_OPEN_API_URL) }
    override val imageSearchAPI: ImageSearchAPI by lazy { kakao.create(ImageSearchAPI::class.java) }
}