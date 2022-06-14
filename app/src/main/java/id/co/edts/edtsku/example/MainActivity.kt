package id.co.edts.edtsku.example

import android.content.Intent
import android.view.LayoutInflater
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
           if (it != null) {
               val file = File(it)
               val uri = FileProvider.getUriForFile(
                   this,
                   "${packageName}.provider",
                   file
               )

               val intent = Intent(Intent.ACTION_VIEW)
               intent.data = uri
               intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
               startActivity(intent)
           }
       }
    }

    override fun getTrackerPageName() = 0

}