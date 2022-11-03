package id.co.edts.edtsku.example.data.configuration

import id.co.edts.edtsku.example.di.ApiService
import id.co.edts.edtsku.example.di.LoginRequest
import id.co.edtslib.data.BaseDataSource

class ConfigurationRemoteDataSource(
    private val apiService: ApiService
) : BaseDataSource() {

    suspend fun login(username: String, password: String) =
        getResult { apiService.login(LoginRequest(username, password, true)) }

    suspend fun loginVisitor() =
        getResult { apiService.loginVisitor() }

    suspend fun getProvinces() =
        getResult { apiService.getProvinces() }

}