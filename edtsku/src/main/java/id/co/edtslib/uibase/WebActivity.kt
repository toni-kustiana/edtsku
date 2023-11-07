package id.co.edtslib.uibase

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.webkit.*
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import id.co.edtslib.databinding.ActivityWebBinding

open class WebActivity: PopupActivity<ActivityWebBinding>() {
    companion object {
        fun open(fragmentActivity: FragmentActivity, url: String, title: String? = null,
                 resultLauncher: ActivityResultLauncher<Intent>? = null) {
            val intent = Intent(fragmentActivity, WebActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            if (title != null) {
                intent.putExtra("title", title)
            }
            intent.putExtra("url", url)
            if (resultLauncher == null) {
                ActivityCompat.startActivity(fragmentActivity, intent, null)
            }
            else {
                resultLauncher.launch(intent)
            }
        }
    }

    protected open fun onPageFinished(url: String?) {
        binding.flProgressBar.isVisible = false
    }
    protected open fun onPageCommitVisible(url: String?) {
        binding.flProgressBar.isVisible = false
    }
    @RequiresApi(Build.VERSION_CODES.M)
    protected open fun  onReceivedError(
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        if (binding.webView.url == request?.url?.toString() && error?.errorCode != -1) {
            showError(error?.errorCode, error?.description?.toString())
        }
    }
    protected open fun onReceivedHttpError(
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?
    ) {
        if (binding.webView.url == request?.url?.toString()) {
            showError(errorResponse?.statusCode, errorResponse?.toString())
        }
    }
    protected open fun onReceivedError(
        errorCode: Int,
        description: String?,
        failingUrl: String?
    ) {
        if (binding.webView.url == failingUrl) {
            showError(errorCode, description)
        }
    }
    protected open fun shouldOverrideUrlLoading(uri: Uri?) = false

    protected open fun getUserAgentAdditionalInfo(): Map<String, Any?>? {
        return null
    }

    protected open fun errorView(): View? = null
    protected open fun showError(errorCode: Int?, errorDescription: String?) {
        binding.flError.isVisible = true
        binding.webView.isVisible = false
    }

    override val bindingInflater: (LayoutInflater) -> ActivityWebBinding
        get() = ActivityWebBinding::inflate

    override fun getTrackerPageName(): String? = null

    override fun setupPopup() {
        setupView()
        initData()
    }

    protected open fun setupView() {
        val errorView = errorView()

        if (errorView != null) {
            binding.flError.addView(errorView)
        }

        binding.webView.userAgentAdditionalInfo = getUserAgentAdditionalInfo()
        binding.flError.isVisible = false
        binding.flProgressBar.isVisible = false

        binding.webView.delegate = object : EdtsWebDelegate {
            override fun onPageFinished(view: WebView?, url: String?) {
                onPageFinished(url)
            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                onPageCommitVisible(url)
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
               onReceivedError(request, error)
            }

            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                onReceivedError(errorCode, description, failingUrl)
            }

            override fun onReceivedHttpError(
                view: WebView?,
                request: WebResourceRequest?,
                errorResponse: WebResourceResponse?
            ) {
                onReceivedHttpError(request, errorResponse)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return shouldOverrideUrlLoading(request?.url)
            }

        }
    }

    private fun initData() {
        val title = intent?.getStringExtra("title")
        if (title != null) {
            setTitle(title)
        }

        val url = intent?.getStringExtra("url")
        if (url?.isNotEmpty() == true) {
            load(url)
        }
    }

    private fun loading() {
        binding.webView.isVisible = true
        binding.flError.isVisible = false
        binding.flProgressBar.isVisible = true
    }

    protected fun load(url: String) {
        loading()
        binding.webView.loadUrl(url)
    }

    protected fun reload() {
        loading()
        binding.webView.reload()
    }
}