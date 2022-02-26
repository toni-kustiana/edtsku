package id.co.edts.edtsku.example

import android.view.LayoutInflater
import id.co.edts.edtsku.example.databinding.ActivityMainBinding
import id.co.edtslib.uibase.BaseActivity
import id.co.edtslib.uibase.ConfirmationHorizDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val configurationViewModel: ConfigurationViewModel by viewModel()

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    //override val viewModel: ConfigurationViewModel by currentScope.inject()

    override fun setup() {
        ConfirmationHorizDialog.show(this, 0, "", "apakah", "button1", "button2", {

        }, {

        })
    }

    override fun getTrackerPageName() = 0

}