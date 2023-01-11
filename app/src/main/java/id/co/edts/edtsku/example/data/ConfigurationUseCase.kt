package id.co.edts.edtsku.example.data

import id.co.edts.edtsku.example.Location
import id.co.edts.edtsku.example.Session
import id.co.edtslib.data.Result
import kotlinx.coroutines.flow.Flow

interface ConfigurationUseCase {
    fun login(): Flow<Result<LoginResponse?>>
    fun loginVisitor(): Flow<Result<Session?>>
    fun refreshToken(): Flow<Result<Session?>>
}