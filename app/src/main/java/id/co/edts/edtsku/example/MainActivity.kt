package id.co.edts.edtsku.example

import android.content.Intent
import android.view.LayoutInflater
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import id.co.edts.edtsku.example.databinding.ActivityMainBinding
import id.co.edtslib.uibase.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val configurationViewModel: ConfigurationViewModel by viewModel()

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup() {
       configurationViewModel.download("https://qlarp-api-dev.arsenadevelopment.eu/test.pdf").observe(this) {
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
       }
    }

    override fun getTrackerPageName() = 0
    override fun canBack() = true
}