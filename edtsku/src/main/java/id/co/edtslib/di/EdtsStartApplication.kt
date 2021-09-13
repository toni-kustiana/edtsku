package id.co.edtslib.di

import android.app.Application
import id.co.edtslib.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import timber.log.Timber

class EdtsKu {
    companion object {
        var baseUrlApi = ""
        fun init(application: Application, baseUrlApi: String, modules: List<Module>) {
            EdtsKu.baseUrlApi = baseUrlApi

            if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
            with(application) {
                startKoin {
                    androidContext(applicationContext)
                    modules(
                        listOf(
                            networkingModule,
                            sharedPreferencesModule,
                            mainViewModel,
                            mainAppModule,
                            mainInteractorModule,
                            mainRepositoryModule
                        )
                    )
                    modules(modules)
                }
            }
        }
    }
}