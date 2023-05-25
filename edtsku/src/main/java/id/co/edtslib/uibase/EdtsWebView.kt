package id.co.edtslib.uibase

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import com.google.gson.Gson
import id.co.edtslib.EdtsKu
import id.co.edtslib.tracker.Tracker

@SuppressLint("SetJavaScriptEnabled")
open class EdtsWebView : WebView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        post {
            setWebContentsDebuggingEnabled(EdtsKu.debugging)

            settings.userAgentString = Gson().toJson(Tracker.getData())
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
        }
    }
}