package id.co.edts.edtsku.example.data.configuration

import id.co.edtslib.data.source.remote.response.Result
import kotlinx.coroutines.flow.Flow

interface IConfigurationRepository {
    fun getContactUs(): Flow<Result<Any>>
}