package com.linitial.kakaoimagesearch.di

import com.linitial.kakaoimagesearch.data.imageSearch.repository.ImageSearchRepository
import com.linitial.kakaoimagesearch.data.imageSearch.repository.ImageSearchRepositoryImpl
import com.linitial.kakaoimagesearch.network.KakaoApiProvider
import com.linitial.kakaoimagesearch.ui.home.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // utils
    single { KakaoApiProvider() }
    
    // repositories
    single<ImageSearchRepository> { ImageSearchRepositoryImpl(get()) }

    // view models
    viewModel { MainViewModel(get()) }
}