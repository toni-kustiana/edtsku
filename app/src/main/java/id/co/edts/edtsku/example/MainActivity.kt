package id.co.edts.edtsku.example

import android.view.LayoutInflater
import android.widget.Toast
import id.co.edts.edtsku.example.databinding.ActivityMainBinding
import id.co.edtslib.uibase.BaseActivity
import id.co.edtslib.uibase.WebActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup() {
        binding.tvClick.setOnClickListener {
            Toast.makeText(this, BuildConfig.APPLICATION_ID, Toast.LENGTH_SHORT).show()
        }
    }

    override fun clonerAllowed() = false
    override fun emulatorAllowed() = false

    override fun getTrackerPageName() = 0

}