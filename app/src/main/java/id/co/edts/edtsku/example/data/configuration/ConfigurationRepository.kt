package id.co.edts.edtsku.example.data.configuration

import id.co.edts.edtsku.example.data.LoginResponse
import id.co.edtslib.data.source.remote.response.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ConfigurationRepository(private val remoteDataSource: ConfigurationRemoteDataSource):
    IConfigurationRepository {
    override fun login(): Flow<Result<LoginResponse?>> = flow {
        val response = remoteDataSource.login("abah", "test")

        when(response.status) {
            Result.Status.SUCCESS -> {
                if (response.data?.data?.id_token != null) {
                    emit(Result.success(response.data?.data))

                }
                else {
                    emit(Result.error<LoginResponse?>(response.code, response.message, null))
                }
            }
            Result.Status.UNAUTHORIZED -> {
                emit(Result.unauthorized<LoginResponse?>(response.message))
            }
            else -> {
                emit(Result.error<LoginResponse?>(response.code, response.message, null))
            }
        }
    }
}