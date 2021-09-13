package id.co.edts.edtsku.example

import androidx.lifecycle.asLiveData
import id.co.edts.edtsku.example.data.ConfigurationUseCase
import id.co.edtslib.uibase.BaseViewModel

class ConfigurationViewModel(private val configurationUseCase: ConfigurationUseCase
): BaseViewModel() {
    fun getContactUs() = configurationUseCase.getContactUs().asLiveData()
}