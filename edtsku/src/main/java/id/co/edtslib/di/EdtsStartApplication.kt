package id.co.edtslib.di

import android.content.Context
import id.co.edtslib.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import timber.log.Timber

open class EdtsStartApplication(context: Context) {
    open fun additionModules() = listOf<Module>()

    init {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(context)
            modules(
                listOf(
                    networkingModule,
                    sharedPreferencesModule,
                    mainViewModel
                )
            )
            modules(additionModules())
        }
    }
}