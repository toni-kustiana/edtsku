package id.co.edts.edtsku.example.data.configuration

import id.co.edts.edtsku.example.di.ApiService
import id.co.edtslib.data.source.remote.response.BaseDataSource

class ConfigurationRemoteDataSource(private val apiService: ApiService) :
    BaseDataSource() {

    suspend fun getContactUs() =
        getResult { apiService.getContactUs() }
}