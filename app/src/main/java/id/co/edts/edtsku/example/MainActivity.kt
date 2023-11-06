package id.co.edts.edtsku.example

import android.view.LayoutInflater
import android.widget.Button
import id.co.edts.edtsku.example.databinding.ActivityMainBinding
import id.co.edtslib.uibase.BaseActivity
import id.co.edtslib.uibase.EdtsWebView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val viewModel: ConfigurationViewModel by viewModel()
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun getTrackerPageName(): String? = null

    override fun setup() {
        val webView = findViewById<EdtsWebView>(R.id.webView)
        webView.loadUrl("http://192.168.30.47:3001/webview/invoice/virtual/A2VAAWE0VH")

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            webView.print()
        }
    }


}