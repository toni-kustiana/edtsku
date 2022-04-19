package id.co.edts.edtsku.example

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import id.co.edts.edtsku.example.data.LoginResponse
import id.co.edts.edtsku.example.databinding.ActivityMainBinding
import id.co.edtslib.data.source.remote.response.ProcessResult
import id.co.edtslib.data.source.remote.response.ProcessResultDelegate
import id.co.edtslib.uibase.BaseActivity
import id.co.edtslib.uibase.ConfirmationHorizDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val configurationViewModel: ConfigurationViewModel by viewModel()

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup() {
       configurationViewModel.login().observe(this) {
           ProcessResult(it, object : ProcessResultDelegate<LoginResponse?> {
               override fun loading() {
               }

               override fun error(code: String?, message: String?) {
                   binding.textview.text = code
               }

               override fun unAuthorize(message: String?) {
                   binding.textview.text = message
               }

               @SuppressLint("SetTextI18n")
               override fun success(data: LoginResponse?) {
                   binding.textview.text = "sukses $data"
               }

               @SuppressLint("SetTextI18n")
               override fun errorConnection() {
                   binding.textview.text = "errorConnection"
               }

               override fun errorSystem() {
                   binding.textview.text = it.message
               }
           })
       }
    }

    override fun getTrackerPageName() = 0

}