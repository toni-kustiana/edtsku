package id.co.edtslib.uibase

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    protected open fun userAgentAdditionalInfo(): Map<String, Any?>? {
        return null
    }

    init {
        post {
            setWebContentsDebuggingEnabled(EdtsKu.debugging)

            val json = Gson().toJson(Tracker.getData())
            val userAgent = Gson().fromJson<MutableMap<String, Any?>>(json, object : TypeToken<MutableMap<String, Any?>>() {}.type)
            userAgentAdditionalInfo()?.forEach {
                userAgent[it.key] = it.value
            }

            settings.userAgentString = Gson().toJson(userAgent)


            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
        }
    }
}