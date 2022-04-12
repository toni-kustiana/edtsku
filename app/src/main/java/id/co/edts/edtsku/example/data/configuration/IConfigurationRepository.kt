package id.co.edts.edtsku.example.data.configuration

import id.co.edts.edtsku.example.data.LoginResponse
import id.co.edtslib.data.source.remote.response.Result
import kotlinx.coroutines.flow.Flow

interface IConfigurationRepository {
    fun login(): Flow<Result<LoginResponse?>>
}