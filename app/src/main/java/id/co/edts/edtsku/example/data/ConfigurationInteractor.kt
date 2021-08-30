package id.co.edts.edtsku.example.data

import id.co.edts.edtsku.example.data.configuration.IConfigurationRepository
import id.co.edtslib.data.source.remote.response.Result
import kotlinx.coroutines.flow.Flow

class ConfigurationInteractor(private val repository: IConfigurationRepository): ConfigurationUseCase {
    override fun getContactUs() = repository.getContactUs()
}