package com.linitial.kakaoimagesearch

import android.app.Application
import com.linitial.kakaoimagesearch.di.appModule
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import java.io.IOException
import java.net.SocketException

class KaKaoImageSearchApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initRxErrorHandle()
        initTimber()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@KaKaoImageSearchApp)
            modules(appModule)
        }
    }

    private fun initRxErrorHandle() {
        RxJavaPlugins.setErrorHandler { e ->
            var error = e
            if (error is UndeliverableException) {
                error = e.cause
            }
            if (error is IOException || error is SocketException) {
                // fine, irrelevant network problem or API that throws on cancellation
                return@setErrorHandler
            }
            if (error is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return@setErrorHandler
            }
            if (error is NullPointerException || error is IllegalArgumentException) {
                // that's likely a bug in the application
                Thread.currentThread().uncaughtExceptionHandler
                    .uncaughtException(Thread.currentThread(), error)
                return@setErrorHandler
            }
            if (error is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().uncaughtExceptionHandler
                    .uncaughtException(Thread.currentThread(), error)
                return@setErrorHandler
            }

            Timber.wtf(error, "Undeliverable exception received, not sure what to do")
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}
