package id.co.edts.edtsku.example

import android.view.LayoutInflater
import android.webkit.WebView
import id.co.edts.edtsku.example.databinding.ActivityMainBinding
import id.co.edtslib.uibase.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup() {
        val webView = findViewById<WebView>(R.id.webView)
        webView.loadUrl("http://adilahsoft.com/test.php")
    }

    override fun clonerAllowed() = false
    override fun emulatorAllowed() = false

    override fun getTrackerPageName(): String? = null

}