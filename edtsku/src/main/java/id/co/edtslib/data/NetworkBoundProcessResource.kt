package id.co.edtslib.data

import id.co.edtslib.data.source.local.HttpHeaderLocalSource
import id.co.edtslib.data.source.remote.SessionRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

abstract class NetworkBoundProcessResource<ResultType, RequestType>(
    private val localDataSource: HttpHeaderLocalSource,
    private val sessionRemoteDataSource: SessionRemoteDataSource
) {
    private val result: Flow<Result<ResultType>> = flow {
        emit(Result.loading())
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