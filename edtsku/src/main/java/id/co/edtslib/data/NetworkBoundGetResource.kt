package id.co.edtslib.data

import id.co.edtslib.data.source.local.HttpHeaderLocalSource
import id.co.edtslib.data.source.remote.SessionRemoteDataSource
import id.co.edtslib.tracker.di.ConfigurationLocalSource
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class NetworkBoundGetResource<ResultType, ResponseType>(
    private val localDataSource: HttpHeaderLocalSource,
    private val sessionRemoteDataSource: SessionRemoteDataSource
): KoinComponent {
    private val configurationLocalSource: ConfigurationLocalSource by inject()

    protected abstract fun getCached(): Flow<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract suspend fun createCall(): Result<ResponseType>
    protected abstract suspend fun saveCallResult(data: ResponseType): ResultType

    private val result: Flow<Result<ResultType>> = flow {
        emit(Result.loading())
        val dbSource = getCached()
        if (shouldFetch(dbSource.first())) {
            localDataSource.setHeader("session_id", configurationLocalSource.getSessionId())
            localDataSource.setHeader("event_id", configurationLocalSource.getEventId().toString())

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
                    localDataSource.setBearerToken(refreshToken)
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
                dbSource.map {
                    Result.success(it)
                }
            )
        }
    }

    open fun isValidData(response: Result<ResponseType>) = response.data != null
    fun asFlow(): Flow<Result<ResultType>> = result
}