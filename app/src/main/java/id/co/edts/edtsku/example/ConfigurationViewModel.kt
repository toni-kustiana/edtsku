package id.co.edts.edtsku.example

import androidx.lifecycle.asLiveData
import id.co.edts.edtsku.example.data.ConfigurationUseCase
import id.co.edtslib.domain.interactor.MainUseCase
import id.co.edtslib.uibase.BaseViewModel

class ConfigurationViewModel(
    private val mainUseCase: MainUseCase,
    private val configurationUseCase: ConfigurationUseCase
) : BaseViewModel() {
    fun download(url: String) = mainUseCase.download(url).asLiveData()
    fun loginVisitor() = configurationUseCase.loginVisitor().asLiveData()
    fun getProvinces() = configurationUseCase.getProvinces().asLiveData()
    fun refreshToken() = configurationUseCase.refreshToken().asLiveData()
}