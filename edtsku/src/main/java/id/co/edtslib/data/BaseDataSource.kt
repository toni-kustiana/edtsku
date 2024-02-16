package id.co.edtslib.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.MalformedJsonException
import id.co.edtslib.data.source.remote.network.AuthInterceptor
import id.co.edtslib.data.source.remote.response.ApiContentResponse
import id.co.edtslib.data.source.remote.response.ApiResponse
import id.co.edtslib.tracker.Tracker
import id.co.edtslib.util.ErrorMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.BufferedSource
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.charset.Charset

/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            val code = response.code()
            if (response.isSuccessful) {
                val body = response.body()
                return if (body != null) {
                    if (body is ApiResponse<*>) {
                        if (body.isSuccess()) {
                            Result.success(body)
                        }
                        else  if (body is ApiContentResponse<*>) {
                            if (body.isSuccess()) {
                                Result.success(body)
                            } else {
                                trackerFailed(reason = body.status, url = response.raw().request.url.toString())
                                Result.error(body.status, body.message, body.data?.content as T?)
                            }
                        }
                        else {
                            trackerFailed(reason = body.status, url = response.raw().request.url.toString())
                            Result.error(body.status, body.message, body.data as T?)
                        }
                    } else {
                        Result.success(body)
                    }
                } else {
                    trackerFailed(reason = "BODYNULL", url = response.raw().request.url.toString())
                    Result.error("BODYNULL", ErrorMessage().connection(), null)
                }
            }
            else {
                if (code == 401) {
                    (return if (response.errorBody() != null) {
                        val bufferedSource: BufferedSource = response.errorBody()!!.source()
                        bufferedSource.request(Long.MAX_VALUE) // Buffer the entire body.

                        val json =
                            bufferedSource.buffer.clone().readString(Charset.forName("UTF8"))

                        try {
                            val badResponse = Gson().fromJson<ApiResponse<Any>?>(
                                json,
                                object : TypeToken<ApiResponse<Any>?>() {}.type
                            )
                            if (badResponse.data != null) {
                                trackerFailed(reason = "401", url = response.raw().request.url.toString())
                                Result.unauthorized(badResponse.message)
                            }
                            else {
                                trackerFailed(
                                    reason = "401",
                                    url = response.raw().request.url.toString()
                                )
                                Result.unauthorized(badResponse.message)
                            }
                        }
                        catch (e: Exception) {
                            trackerFailed(
                                reason = "401",
                                url = response.raw().request.url.toString()
                            )
                            Result.unauthorized(json)
                        }
                    } else {
                        trackerFailed(
                            reason = "401",
                            url = response.raw().request.url.toString()
                        )
                        Result.unauthorized(null)
                    })
                } else
                    if (code == 400 || code == 500) {
                        if (response.errorBody() != null) {
                            val bufferedSource: BufferedSource = response.errorBody()!!.source()
                            bufferedSource.request(Long.MAX_VALUE) // Buffer the entire body.

                            val json = bufferedSource.buffer.clone().readString(Charset.forName("UTF8"))

                            val badResponse = Gson().fromJson<ApiResponse<Any>?>(
                                json,
                                object : TypeToken<ApiResponse<Any>?>() {}.type
                            )
                            return if (code == 500) {
                                trackerFailed(reason = "SystemError", url = response.raw().request.url.toString())
                                Result.error("SystemError", badResponse.message, null)

                            } else {
                                trackerFailed(reason = badResponse.status, url = response.raw().request.url.toString())
                                Result.error(badResponse.status, badResponse.message, null)
                            }
                        }
                    }
                    else if (code == 503) {
                        trackerFailed(reason = "503", url = response.raw().request.url.toString())
                        return Result.error("503", ErrorMessage().http503(), null)
                    }
            }
            return Result.error(code.toString(), response.message(), null)
        } catch (e: Exception) {
            return if (e is ConnectException || e is UnknownHostException ||
                e is MalformedJsonException || e is SocketTimeoutException) {
                trackerFailed(reason = e.message, url = AuthInterceptor.url)
                Result.error("ConnectionError", ErrorMessage().connection(), null)
            } else {
                trackerFailed(reason = e.message, url = AuthInterceptor.url)
                Result.error("999", ErrorMessage().system(e.message), null)
            }
        }
    }

    private suspend fun trackerFailed(reason: String?, url: String?) {
        try {
            withContext(Dispatchers.Main) {
                try {
                    Tracker.trackSubmissionFailed(name = "api_failed",
                        category = "",
                        reason = reason,
                        details = url)
                }
                catch (ignore: Exception) {

                }
            }
        }
        catch (ignore: Exception) {

        }
    }

}