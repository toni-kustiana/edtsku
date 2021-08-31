package id.co.edtslib.data.source.remote.response

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.MalformedJsonException
import id.co.edtslib.domain.model.ApiResponse
import id.co.edtslib.util.ErrorMessage
import okio.BufferedSource
import retrofit2.Response
import java.net.ConnectException
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
                        } else {
                            Result.error(body.status, body.message)
                        }
                    } else {
                        Result.success(body)
                    }
                } else {
                    Result.error("BODYNULL", ErrorMessage().connection(), null)
                }
            }
            else {
                if (code == 401) {
                    return Result.unauthorized()
                } else
                    if (code == 400 || code == 500) {
                        @Suppress("BlockingMethodInNonBlockingContext")
                        if (response.errorBody() != null) {
                            val bufferedSource: BufferedSource = response.errorBody()!!.source()
                            bufferedSource.request(Long.MAX_VALUE) // Buffer the entire body.

                            val json = bufferedSource.buffer.clone().readString(Charset.forName("UTF8"))

                            val badResponse = Gson().fromJson<ApiResponse<Any>?>(
                                json,
                                object : TypeToken<ApiResponse<Any>?>() {}.type
                            )
                            return Result.error(badResponse.status, badResponse.message)
                        }
                    }
                    else if (code == 503) {
                        return Result.error("503", ErrorMessage().http503())
                    }
            }
            return Result.error(code.toString(), response.message())
        } catch (e: Exception) {
            return if (e is ConnectException || e is UnknownHostException ||
                e is MalformedJsonException) {
                Result.error("ConnectionError", ErrorMessage().connection())
            } else {
                Result.error("SystemError", ErrorMessage().system(e.message))
            }
        }
    }


}