package id.co.edtslib.di

import android.app.Application
import id.co.edtslib.tracker.Tracker
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class EdtsKu {
    companion object {
        var baseUrlApi = ""
        var debugging = false

        fun init(application: Application, baseUrlApi: String, modules: List<Module>) {
            EdtsKu.baseUrlApi = baseUrlApi

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


        fun init(application: Application, baseUrlApi: String, trackerApi: String,
                 trackerToken: String, modules: List<Module>) {

            EdtsKu.baseUrlApi = baseUrlApi

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
                    Tracker.init(trackerApi, trackerToken, this)

                }
            }
        }
    }
}