package id.co.edtslib.uibase

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.AttributeSet
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
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

    var userAgentAdditionalInfo: Map<String, Any?>? = null
    var delegate: EdtsWebDelegate? = null

    init {
        post {
            setWebContentsDebuggingEnabled(EdtsKu.debugging)

            val json = Gson().toJson(Tracker.getData())
            val userAgent = Gson().fromJson<MutableMap<String, Any?>>(json, object : TypeToken<MutableMap<String, Any?>>() {}.type)
            userAgentAdditionalInfo?.forEach {
                userAgent[it.key] = it.value
            }

            settings.userAgentString = Gson().toJson(userAgent)

            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true

            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    delegate?.onPageFinished(view, url)
                }

                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    super.onPageCommitVisible(view, url)

                    delegate?.onPageFinished(view, url)
                }

                @RequiresApi(Build.VERSION_CODES.M)
                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    delegate?.onReceivedError(view, request, error)
                    super.onReceivedError(view, request, error)
                }

                override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
                ) {
                    delegate?.onReceivedHttpError(view, request, errorResponse)
                    super.onReceivedHttpError(view, request, errorResponse)
                }

                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    delegate?.onReceivedError(view, errorCode, description, failingUrl)
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    val url = request?.url.toString()
                    if (url.startsWith("edtsweb://print")) {
                        print()
                        return true
                    }

                    return delegate?.shouldOverrideUrlLoading(view, request) == true
                }
            }
        }
    }

    fun print(name: String? = null) {
        (context.getSystemService(Context.PRINT_SERVICE) as? PrintManager)?.let { printManager ->

            val jobName = name ?: "Document"

            // Get a print adapter instance
            val printAdapter = createPrintDocumentAdapter(jobName)

            printManager.print(
                jobName,
                printAdapter,
                PrintAttributes.Builder().build()
            ).also { printJob ->

                // Save the job object for later status checking
                //printJobs += printJob
            }
        }
    }
}