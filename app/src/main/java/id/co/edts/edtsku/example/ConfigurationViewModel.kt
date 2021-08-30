package id.co.edts.edtsku.example

import androidx.lifecycle.asLiveData
import id.co.edts.edtsku.example.data.ConfigurationUseCase
import id.co.edts.edtsku.example.data.configuration.ConfigurationRepository
import id.co.edtslib.uibase.BaseViewModel

class ConfigurationViewModel(private val useCase: ConfigurationUseCase): BaseViewModel() {
    fun getContactUs() = useCase.getContactUs().asLiveData()
}