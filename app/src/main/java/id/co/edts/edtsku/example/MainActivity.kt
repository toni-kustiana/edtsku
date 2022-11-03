package id.co.edts.edtsku.example

import android.view.LayoutInflater
import id.co.edts.edtsku.example.databinding.ActivityMainBinding
import id.co.edtslib.data.ProcessResult
import id.co.edtslib.data.ProcessResultDelegate
import id.co.edtslib.uibase.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val configurationViewModel: ConfigurationViewModel by viewModel()

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup() {
//        loginVisitor()
//        getProvinces()
        refreshToken()
        /*configurationViewModel.download("https://qlarp-api-dev.arsenadevelopment.eu/test.pdf")
            .observe(this) {
                if (it?.file != null) {
                    val file = File(it.file!!)
                    val uri = FileProvider.getUriForFile(
                        this,
                        "${packageName}.provider",
                        file
                    )

                    val extension = MimeTypeMap.getFileExtensionFromUrl(it.file)
                    if (extension != null) {

                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                        intent.setDataAndType(uri, it.type)
                        startActivity(intent)
                    }
                }
            }*/
    }

    override fun getTrackerPageName() = 0

    private fun loginVisitor() {
        configurationViewModel.loginVisitor().observe(this) {
            ProcessResult(it, object : ProcessResultDelegate<Session?> {
                override fun loading() {
                }

                override fun error(code: String?, message: String?) {
                }

                override fun unAuthorize(message: String?) {
                }

                override fun success(data: Session?) {
//                    getProvinces()
                }

                override fun errorConnection() {
                }

                override fun errorSystem() {
                }
            })
        }
    }

    private fun refreshToken() {
        configurationViewModel.refreshToken().observe(this) {
            ProcessResult(it, object : ProcessResultDelegate<Session?> {
                override fun loading() {
                }

                override fun error(code: String?, message: String?) {
                }

                override fun unAuthorize(message: String?) {
                }

                override fun success(data: Session?) {
//                    getProvinces()
                }

                override fun errorConnection() {
                }

                override fun errorSystem() {
                }
            })
        }
    }

    private fun getProvinces() {
        configurationViewModel.getProvinces().observe(this) {
            ProcessResult(it, object : ProcessResultDelegate<List<Location>?> {
                override fun loading() {
                }

                override fun error(code: String?, message: String?) {
                }

                override fun unAuthorize(message: String?) {
                }

                override fun success(data: List<Location>?) {
                }

                override fun errorConnection() {
                }

                override fun errorSystem() {
                }
            })
        }
    }

}