package id.co.edtslib.data

import id.co.edtslib.data.source.local.HttpHeaderLocalSource
import id.co.edtslib.data.source.remote.SessionRemoteDataSource
import id.co.edtslib.tracker.di.ConfigurationLocalSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class NetworkBoundProcessResource<ResultType, RequestType>(
    private val localDataSource: HttpHeaderLocalSource,
    private val sessionRemoteDataSource: SessionRemoteDataSource
): KoinComponent {
    private val configurationLocalSource: ConfigurationLocalSource by inject()

    private val result: Flow<Result<ResultType>> = flow {
        emit(Result.loading())

        localDataSource.setHeader("session_id", configurationLocalSource.getSessionId())
        localDataSource.setHeader("event_id", configurationLocalSource.getEventId().toString())

        val response = createCall()
        when (response.status) {
            Result.Status.SUCCESS -> {
                if (isValidData(response)) {
                    val responseData = callBackResult(response.data!!)
                    emit(Result.success(responseData))
                } else {
                    emit(
                        Result.error(
                            response.code,
                            response.message,
                            onResponseError(response.data)
                        )
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
                                localDataSource.setHeader("refresh-token", tokenResponse.data.data.refreshToken)
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
                emit(
                    Result.error(
                        response.code,
                        response.message,
                        onResponseError(response.data)
                    )
                )
            }
        }
    }

    open fun isValidData(response: Result<RequestType>) = response.data != null

    protected open suspend fun onResponseError(data: Any?): ResultType? {
        return null
    }

    protected abstract suspend fun createCall(): Result<RequestType>

    protected abstract suspend fun callBackResult(data: RequestType): ResultType

    fun asFlow(): Flow<Result<ResultType>> = result
}