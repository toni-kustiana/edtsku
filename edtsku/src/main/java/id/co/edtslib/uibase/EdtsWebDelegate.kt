package id.co.edtslib.uibase

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView

interface EdtsWebDelegate {
    fun onPageFinished(view: WebView?, url: String?)
    fun onPageCommitVisible(view: WebView?, url: String?)
    fun  onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    )
    fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?
    )
    fun onReceivedError(
        view: WebView?,
        errorCode: Int,
        description: String?,
        failingUrl: String?
    )
    fun shouldOverrideUrlLoading(view: WebView?,
                                 request: WebResourceRequest?
    ): Boolean

    fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)
}