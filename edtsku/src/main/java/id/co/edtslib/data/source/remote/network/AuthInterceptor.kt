package id.co.edtslib.data.source.remote.network

import id.co.edtslib.data.source.local.HttpHeaderLocalSource
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * A {@see RequestInterceptor} that adds an auth token to requests
 */
class AuthInterceptor(private val httpHeaderLocalSource: HttpHeaderLocalSource,
                      private val apps: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val headers = httpHeaderLocalSource.getCached()
        val builder = chain.request().newBuilder()
        if (headers != null) {
            for ((k, v) in headers) {
                if (v != null) {
                    builder.addHeader(k, v)
                }
            }
        }
        builder.addHeader("apps", apps)

        return chain.proceed(builder.build())
    }
}