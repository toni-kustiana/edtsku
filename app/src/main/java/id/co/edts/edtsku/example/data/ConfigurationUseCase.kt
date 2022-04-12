package id.co.edts.edtsku.example.data

import id.co.edtslib.data.source.remote.response.Result
import kotlinx.coroutines.flow.Flow

interface ConfigurationUseCase {
    fun login(): Flow<Result<LoginResponse?>>
}