package id.co.edts.edtsku.example.data.configuration

import id.co.edtslib.data.source.remote.response.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ConfigurationRepository(private val remoteDataSource: ConfigurationRemoteDataSource):
    IConfigurationRepository {
    override fun getContactUs(): Flow<Result<Any>> = flow {

        remoteDataSource.getContactUs()
        emit(Result.success(true))
    }
}