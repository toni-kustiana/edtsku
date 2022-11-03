package id.co.edtslib

import android.app.Application
import id.co.edtslib.di.*
import id.co.edtslib.tracker.Tracker
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import javax.net.ssl.TrustManagerFactory

class EdtsKu {
    companion object {
        var sslDomain = ""
        var sslPinner = ""
        var baseUrlApi = ""
        var refreshTokenUrlApi = ""
        var debugging = false
        var trustManagerFactory: TrustManagerFactory? = null
        var versionName = ""
        var isTablet = false

        fun init(application: Application, baseUrlApi: String, refreshTokenPath: String, modules: List<Module>) {
            EdtsKu.baseUrlApi = baseUrlApi
            refreshTokenUrlApi = refreshTokenPath

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
                            mainRepositoryModule,
                            sessionRepositoryModule,
                            sessionModule
                        )
                    )
                    modules(modules)
                }
            }
        }


        fun init(application: Application, baseUrlApi: String, trackerApi: String, refreshTokenPath: String,
                 trackerToken: String, modules: List<Module>) {

            EdtsKu.baseUrlApi = baseUrlApi
            refreshTokenUrlApi = refreshTokenPath

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
                            mainRepositoryModule,
                            sessionRepositoryModule,
                            sessionModule
                        )
                    )
                    modules(modules)
                    Tracker.init(trackerApi, trackerToken, this)

                }
            }
        }

        fun init(application: Application, baseUrlApi: String, refreshTokenPath: String, modules: List<Module>,
                 initEx:  (koin: KoinApplication) -> Unit ) {

            EdtsKu.baseUrlApi = baseUrlApi
            refreshTokenUrlApi = refreshTokenPath

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
                            mainRepositoryModule,
                            sessionRepositoryModule,
                            sessionModule
                        )
                    )
                    modules(modules)
                    initEx(this)

                }
            }
        }
    }
}