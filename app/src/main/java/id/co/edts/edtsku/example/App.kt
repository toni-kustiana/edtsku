package id.co.edts.edtsku.example

import android.app.Application
import id.co.edts.edtsku.example.di.appModule
import id.co.edts.edtsku.example.di.repositoryModule
import id.co.edts.edtsku.example.di.viewModelModule
import id.co.edtslib.di.EdtsKu

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        EdtsKu.init(this, "http://api.edtsku.com",
            listOf(appModule, repositoryModule, viewModelModule))
    }
}