package id.co.edts.edtsku.example.data.configuration

import id.co.edts.edtsku.example.DataMapper.provinceListMapResponseToDomain
import id.co.edts.edtsku.example.DataMapper.sessionMapResponseToDomain
import id.co.edts.edtsku.example.Location
import id.co.edts.edtsku.example.Session
import id.co.edts.edtsku.example.data.LocationResponse
import id.co.edts.edtsku.example.data.LoginResponse
import id.co.edtslib.data.NetworkBoundProcessResource
import id.co.edtslib.data.Result
import id.co.edtslib.data.source.local.HttpHeaderLocalSource
import id.co.edtslib.data.source.remote.SessionRemoteDataSource
import id.co.edtslib.data.source.remote.response.ApiContentResponse
import id.co.edtslib.data.source.remote.response.ApiResponse
import id.co.edtslib.data.source.remote.response.SessionResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ConfigurationRepository(
    private val localSource: HttpHeaderLocalSource,
    private val sessionRemoteDataSource: SessionRemoteDataSource,
    private val remoteDataSource: ConfigurationRemoteDataSource
) : IConfigurationRepository {
    override fun login(): Flow<Result<LoginResponse?>> = flow {
        val response = remoteDataSource.login("abah", "test")

        when (response.status) {
            Result.Status.SUCCESS -> {
                if (response.data?.data?.id_token != null) {
                    emit(Result.success(response.data?.data))

                } else {
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

    override fun loginVisitor() =
        object : NetworkBoundProcessResource<Session?, ApiResponse<SessionResponse>>(
            localSource, sessionRemoteDataSource
        ) {
            override suspend fun createCall() =
                remoteDataSource.loginVisitor()

            override suspend fun callBackResult(data: ApiResponse<SessionResponse>): Session? {
                val dataMapper = sessionMapResponseToDomain(data.data)
                if (dataMapper?.token != null) {
                    localSource.setBearerToken(dataMapper.token!!)
                }
                if (dataMapper?.refreshToken != null) {
                    localSource.setHeader("refresh-token", dataMapper.refreshToken)
                }
                return dataMapper
            }

        }.asFlow()

    override fun getProvinces() =
        object : NetworkBoundProcessResource<List<Location>?, ApiContentResponse<List<LocationResponse>>>(
            localSource, sessionRemoteDataSource
        ) {
            override suspend fun createCall() =
                remoteDataSource.getProvinces()

            override suspend fun callBackResult(data: ApiContentResponse<List<LocationResponse>>): List<Location>? {
                return provinceListMapResponseToDomain(data.data!!.content)
            }

        }.asFlow()

    override fun refreshToken() =
        object : NetworkBoundProcessResource<Session?, ApiResponse<SessionResponse>>(
            localSource, sessionRemoteDataSource
        ) {


            override suspend fun callBackResult(data: ApiResponse<SessionResponse>): Session? {
                val dataMapper = sessionMapResponseToDomain(data.data)
                if (dataMapper?.token != null) {
                    localSource.setBearerToken(dataMapper.token!!)
                }
                if (dataMapper?.refreshToken != null) {
                    localSource.setHeader("refresh-token", dataMapper.refreshToken)
                }
                return dataMapper
            }

            override suspend fun createCall(): Result<ApiResponse<SessionResponse>> {
                val cachedHeaders = localSource.getCached()
                val refreshToken = cachedHeaders?.get("refresh-token")
                return sessionRemoteDataSource.refreshToken(refreshToken ?: "")
            }

        }.asFlow()

}