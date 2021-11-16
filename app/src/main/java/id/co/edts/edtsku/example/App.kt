package id.co.edts.edtsku.example

import android.app.Application
import id.co.edts.edtsku.example.di.appModule
import id.co.edts.edtsku.example.di.repositoryModule
import id.co.edts.edtsku.example.di.viewModelModule
import id.co.edtslib.BuildConfig
import id.co.edtslib.di.EdtsKu
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        EdtsKu.init(this, "http://emart-dev.elevenia.co.id",
            listOf(appModule, repositoryModule, viewModelModule))
    }
}