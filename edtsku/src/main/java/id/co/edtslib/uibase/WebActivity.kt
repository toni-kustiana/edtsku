package id.co.edtslib.uibase

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.webkit.*
import android.widget.Toast
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

    protected open fun errorView(): View? = null
    protected open fun showError(errorCode: Int?, errorDescription: String?) {
        Toast.makeText(this, "$errorCode :: $errorDescription", Toast.LENGTH_SHORT).show()
        binding.flError.isVisible = true
    }

    override val bindingInflater: (LayoutInflater) -> ActivityWebBinding
        get() = ActivityWebBinding::inflate

    override fun getTrackerPageName() = 0

    override fun setupPopup() {
        setupView()
        setupListener()
        initData()
    }

    protected open fun setupView() {
        val errorView = errorView()

        if (errorView != null) {
            binding.flError.addView(errorView)
        }

        binding.flError.isVisible = false
        binding.flProgressBar.isVisible = false
        binding.swipeRefreshLayout.isEnabled = true

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                binding.flProgressBar.isVisible = false

                binding.swipeRefreshLayout.isEnabled = true
                binding.swipeRefreshLayout.isRefreshing = false
            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)

                binding.flProgressBar.isVisible = false

                binding.swipeRefreshLayout.isEnabled = true
                binding.swipeRefreshLayout.isRefreshing = false
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                if (view?.url == request?.url?.toString() && error?.errorCode != -1) {
                    showError(error?.errorCode, error?.description?.toString())
                }
                super.onReceivedError(view, request, error)
            }

            override fun onReceivedHttpError(
                view: WebView?,
                request: WebResourceRequest?,
                errorResponse: WebResourceResponse?
            ) {
                if (view?.url == request?.url?.toString()) {
                    showError(errorResponse?.statusCode, errorResponse?.toString())
                }
                super.onReceivedHttpError(view, request, errorResponse)
            }

            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                if (view?.url == failingUrl) {
                    showError(errorCode, description)
                }
            }
        }
    }

    private fun setupListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            reload()
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
        binding.flError.isVisible = false
        binding.flProgressBar.isVisible = true
        binding.swipeRefreshLayout.isEnabled = false
    }

    private fun load(url: String) {
        loading()
        binding.webView.loadUrl(url)
    }

    private fun reload() {
        loading()
        binding.webView.reload()
    }
}