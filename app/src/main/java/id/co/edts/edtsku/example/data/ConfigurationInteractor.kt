package id.co.edts.edtsku.example.data

import id.co.edts.edtsku.example.data.configuration.IConfigurationRepository

class ConfigurationInteractor(private val repository: IConfigurationRepository): ConfigurationUseCase {
    override fun login() = repository.login()
}