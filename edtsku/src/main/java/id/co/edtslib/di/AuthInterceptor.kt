package id.co.edtslib.di

import id.co.edtslib.util.CommonUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * A {@see RequestInterceptor} that adds an auth token to requests
 */
class AuthInterceptor(private val key: String?) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val headers = mutableMapOf<String, String>()
        if (key != null) {
            headers["Key"] = CommonUtil.hexToAscii(key)
        }

        val builder = chain.request().newBuilder()
        for ((k, v) in headers) {
            builder.addHeader(k, v)
        }
        return chain.proceed(builder.build())
    }
}