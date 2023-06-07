package id.co.edts.edtsku.example

import id.co.edtslib.uibase.WebActivity

class MainActivity : WebActivity() {

    override fun getUserAgentAdditionalInfo(): Map<String, Any?> {
        val map = HashMap<String, Any?>()
        map["applicationNameForUserAgent"] = "klikindomaretmobile"

        return map
    }

    override fun setupPopup() {
        super.setupPopup()

        binding.webView.loadUrl("http://www.adilahsoft.com/test.php")
    }
}