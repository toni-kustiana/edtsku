package id.co.edts.edtsku.example

import android.view.LayoutInflater
import id.co.edts.edtsku.example.databinding.ActivityMainBinding
import id.co.edtslib.uibase.BaseActivity
import id.co.edtslib.uibase.WebActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup() {
        WebActivity.open(this, "https://qlarp-api.arsenadevelopment.eu/abah1.html")
    }

    override fun clonerAllowed() = false
    override fun emulatorAllowed() = false

    override fun getTrackerPageName() = 0

}