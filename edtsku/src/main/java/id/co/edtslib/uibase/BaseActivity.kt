package id.co.edtslib.uibase

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.addCallback
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
    open fun clonerAllowed() = true
    open fun emulatorAllowed() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)

        remoteConfigSetup()

        if (! clonerAllowed()) {
            if (CheckCloner(this).check()) {
                finish()
            }
            else
                if (emulatorAllowed()) {
                    setup()
                }
            else if (isEmulator()) {
                finish()
                }
            else {
                setup()
                }
        }
        else {
            if (emulatorAllowed()) {
                setup()
            }
            else if (isEmulator()) {
                finish()
            }
            else {
                setup()
            }
        }

        if (getTrackerPageName() > 0) {
            Tracker.trackPage(getString(getTrackerPageName()))
        }

        onBackPressedDispatcher.addCallback {
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
                            this@BaseActivity,
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
                        finish()
                    } catch (e: IllegalArgumentException) {
                        // nothing to do
                    }
                }
            }
        }
        //baseViewModel.trackFlush()
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

    private fun isEmulator(): Boolean {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("sdk_gphone64_arm64")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator"))
    }
}