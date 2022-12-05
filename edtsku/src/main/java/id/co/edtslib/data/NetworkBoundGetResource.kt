package id.co.edtslib.data

import id.co.edtslib.data.source.local.HttpHeaderLocalSource
import id.co.edtslib.data.source.remote.SessionRemoteDataSource
import kotlinx.coroutines.flow.*

abstract class NetworkBoundGetResource<ResultType, RequestType>(
    private val localDataSource: HttpHeaderLocalSource,
    private val sessionRemoteDataSource: SessionRemoteDataSource
) {
    private val result: Flow<Result<ResultType>> = flow {
        emit(Result.loading())
        val dbSource = getCached().first()
        if (shouldFetch(dbSource)) {
            val response = createCall()
            when (response.status) {
                Result.Status.SUCCESS -> {
                    if (isValidData(response)) {
                        response.data?.let { saveCallResult(it) }
                        emitAll(
                            getCached().map {
                                Result.success(it)
                            }
                        )
                    } else {
                        emitAll(
                            getCached().map {
                                Result.error(
                                    response.code,
                                    response.message,
                                    it
                                )
                            }
                        )
                    }
                }
                Result.Status.UNAUTHORIZED -> {
                    val cachedHeaders = localDataSource.getCached()
                    val refreshToken = cachedHeaders?.get("refresh-token")
                    if (refreshToken != null) {
                        val tokenResponse = sessionRemoteDataSource.refreshToken(refreshToken)
                        when (tokenResponse.status) {
                            Result.Status.SUCCESS -> {
                                if (tokenResponse.data?.data != null &&
                                    tokenResponse.data.isSuccess()
                                ) {
                                    localDataSource.setBearerToken(tokenResponse.data.data.token)
                                    localDataSource.setHeader(
                                        "refresh-token",
                                        tokenResponse.data.data.refreshToken
                                    )
                                }
                                emitAll(asFlow())
                            }
                            else -> {
                                localDataSource.logout()
                                emit(Result.unauthorized<ResultType>(response.message))
                            }
                        }
                    } else {
                        localDataSource.logout()
                        emit(Result.unauthorized<ResultType>(response.message))
                    }
                }
                else -> {
                    emitAll(
                        getCached().map {
                            Result.error(
                                response.code,
                                response.message,
                                it
                            )
                        }
                    )
                }
            }
        } else {
            emitAll(
                getCached().map {
                    Result.success(it)
                }
            )
        }
    }

    open fun isValidData(response: Result<RequestType>) = response.data != null

    protected abstract fun getCached(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Result<RequestType>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Result<ResultType>> = result
}