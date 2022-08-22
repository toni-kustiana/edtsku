package id.co.edtslib.uibase

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import id.co.edtslib.R
import id.co.edtslib.tracker.Tracker
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseActivity<viewBinding: ViewBinding>: AppCompatActivity() {
    private val baseViewModel: BaseViewModel by viewModel()
    private var _binding: ViewBinding? = null
    private var toastQuit: Toast? = null
    private var quit: Boolean = false
    private var quitRunnable: Runnable? = null
    private var handler: Handler? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: viewBinding
        get() = _binding as viewBinding

    abstract val bindingInflater: (LayoutInflater) -> viewBinding
    abstract fun getTrackerPageName(): Int
    abstract fun setup()
    
    open fun canBack() = false
    open fun isHomeActivity() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)

        remoteConfigSetup()

        setup()

        if (getTrackerPageName() > 0) {
            Tracker.trackPage(getString(getTrackerPageName()))
        }

        //baseViewModel.trackFlush()
    }

    override fun onDestroy() {
        super.onDestroy()
        //_binding = null
    }

    override fun onBackPressed() {
        if (canBack()) {
            if (isHomeActivity()) {
                if (quitRunnable != null) {
                    handler?.removeCallbacks(quitRunnable!!)
                }

                if (quit) {
                    toastQuit?.cancel()
                    finishAffinity()
                } else {
                    quit = true

                    quitRunnable = Runnable {
                        toastQuit?.cancel()
                        quit = false
                    }

                    toastQuit = Toast.makeText(
                        this,
                        R.string.tap_for_quit,
                        Toast.LENGTH_LONG
                    )
                    toastQuit?.show()

                    handler = Handler(Looper.myLooper()!!)
                    if (quitRunnable != null) {
                        handler?.postDelayed(quitRunnable!!, 3500)
                    }
                }
            }
            else {
                try {
                    super.onBackPressed()
                } catch (e: IllegalArgumentException) {
                    // nothing to do
                }
            }
        }
    }

    private fun remoteConfigSetup() {
        val uri = "@xml/remote_config"
        val resId = resources.getIdentifier(uri, "xml", packageName)
        if (resId != 0) {
            val mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
            mFirebaseRemoteConfig.setDefaultsAsync(resId)
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(60)
                .setFetchTimeoutInSeconds(5)
                .build()
            mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
            mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener {
                // nothing to do
            }
        }
    }
}