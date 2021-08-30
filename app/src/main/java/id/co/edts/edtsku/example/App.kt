package id.co.edts.edtsku.example

import android.app.Application
import id.co.edtslib.di.EdtsStartApplication

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        TestProjectStartApplication(this)
    }
}