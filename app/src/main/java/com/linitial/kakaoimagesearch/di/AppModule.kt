package com.linitial.kakaoimagesearch.di

import com.linitial.kakaoimagesearch.data.imageSearch.repository.ImageSearchRepository
import com.linitial.kakaoimagesearch.data.imageSearch.repository.ImageSearchRepositoryImpl
import com.linitial.kakaoimagesearch.network.KakaoApiProvider
import com.linitial.kakaoimagesearch.network.KakaoApiProviderImpl
import com.linitial.kakaoimagesearch.ui.home.MainViewModel
import com.linitial.kakaoimagesearch.util.DeviceManager
import com.linitial.kakaoimagesearch.util.ResourceManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // utils
    single { DeviceManager(androidContext()) }
    single { ResourceManager(androidContext()) }
    single<KakaoApiProvider> { KakaoApiProviderImpl() }

    // repositories
    single<ImageSearchRepository> { ImageSearchRepositoryImpl(get()) }

    // view models
    viewModel { MainViewModel(get(), get(), get()) }
}