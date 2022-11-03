package id.co.edts.edtsku.example.data.configuration

import id.co.edts.edtsku.example.Location
import id.co.edts.edtsku.example.Session
import id.co.edts.edtsku.example.data.LoginResponse
import id.co.edtslib.data.Result
import kotlinx.coroutines.flow.Flow

interface IConfigurationRepository {
    fun login(): Flow<Result<LoginResponse?>>
    fun loginVisitor(): Flow<Result<Session?>>
    fun getProvinces(): Flow<Result<List<Location>?>>
    fun refreshToken(): Flow<Result<Session?>>
}