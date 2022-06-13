package id.co.edts.edtsku.example

import androidx.lifecycle.asLiveData
import id.co.edtslib.domain.interactor.MainUseCase
import id.co.edtslib.uibase.BaseViewModel

class ConfigurationViewModel(private val mainUseCase: MainUseCase): BaseViewModel() {
    fun download(url: String) = mainUseCase.download(url).asLiveData()
}