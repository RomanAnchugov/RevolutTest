package ru.romananchugov.revoluttest

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.romananchugov.revoluttest.feature.FeatureManager
import timber.log.Timber

class App : Application(){
    override fun onCreate() {
        super.onCreate()

        initTimber()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(applicationContext)
            modules(FeatureManager.featureKoidModules)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(
                Timber.DebugTree()
            )
        }
    }
}