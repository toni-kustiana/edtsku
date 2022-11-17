package id.co.edts.edtsku.example

import android.view.LayoutInflater
import id.co.edts.edtsku.example.databinding.ActivityMainBinding
import id.co.edtslib.uibase.BaseActivity
import id.co.edtslib.uibase.WebActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup() {
        binding.tvClick.setOnClickListener {
            WebActivity.open(this, "https://sg-edts.com/", "Edts")
        }
    }

    override fun getTrackerPageName() = 0

}