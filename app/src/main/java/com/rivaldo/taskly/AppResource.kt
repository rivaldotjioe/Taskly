package com.rivaldo.taskly

import android.app.Application
import com.rivaldo.taskly.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppResource : Application() {

    override fun onCreate() {
        super.onCreate()

        //initialize koin dependency injection here
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@AppResource)
            modules(
                listOf(
                    networkModule,
                    localDataModule,
                    repositoryModule,
//                    repositoryDummyModule,
                    useCaseModule,
                    viewModelModule,
                )
            )
        }
    }
}