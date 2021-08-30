package id.co.edtslib.uibase

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseActivity<viewBinding: ViewBinding>: AppCompatActivity() {
    private val baseViewModel: BaseViewModel by viewModel()
    private var _binding: ViewBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: viewBinding
        get() = _binding as viewBinding

    abstract val bindingInflater: (LayoutInflater) -> viewBinding
    abstract fun getPageViewName(): Int
    abstract fun setup()
    open fun canBack() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)

        remoteConfigSetup()

        setup()

        if (getPageViewName() > 0) {
            baseViewModel.trackPage(getString(getPageViewName()))
        }

        baseViewModel.trackFlush()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onBackPressed() {
        if (canBack()) {
            try {
                super.onBackPressed()
            }
            catch (e: IllegalArgumentException) {
                // nothing to do
            }
        }
    }

    private fun remoteConfigSetup() {
        /*val uri = "@xml/remote_config"
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
        }*/
    }
}