package id.co.edtslib.data.source.remote.network

import id.co.edtslib.EdtsKu
import id.co.edtslib.data.source.local.HttpHeaderLocalSource
import id.co.edtslib.util.SecurityUtil
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.security.PrivateKey

/**
 * A {@see RequestInterceptor} that adds an auth token to requests
 *
 * @constructor Create an auth interceptor
 * */
class AuthInterceptor(
    private val httpHeaderLocalSource: HttpHeaderLocalSource,
    private val apps: String
) : Interceptor {
    companion object {
        var url: String? = null // sementara karena belum tau callback ke get result
    }

    private var privateKey: PrivateKey? = null

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val headers = httpHeaderLocalSource.getCached()
        val builder = chain.request().newBuilder()
        val requestCopy = builder.build()

        if (headers != null) {
            for ((k, v) in headers) {
                if (v != null) {
                    try {
                        builder.addHeader(k, v)
                    }
                    catch (ignore: Exception) {

                    }
                }
            }
        }
        builder.addHeader("apps", apps)
        if (EdtsKu.privateKeyFileContent != null && EdtsKu.defaultPayload != null &&
            EdtsKu.enableSignature) {
            privateKey = SecurityUtil.getPrivateKeyFromKeyStore(EdtsKu.privateKeyFileContent!!)
            if (privateKey != null) {
                val signature = getSignature(requestCopy, EdtsKu.defaultPayload!!)
                builder.addHeader("signature", signature)
            }
        }

        if (EdtsKu.sendSha1 && EdtsKu.signatures?.isNotEmpty() == true) {
            builder.addHeader("sha1", EdtsKu.signatures!!.joinToString(","))
        }

        builder.removeHeader("pathSignature")
        builder.removeHeader("usingAppsSignature")

        url = requestCopy.url.toString()

        return chain.proceed(builder.build())
    }

    private fun getSignature(requestCopy: Request, defaultPayload: String): String {
        val method = requestCopy.method
        if (method == "GET") {
            return SecurityUtil.signWithPayload(defaultPayload, privateKey)
        }

        val contentType = requestCopy.body?.contentType()?.toString()
        val usingAppsSignature = requestCopy.header("usingAppsSignature")
        if (contentType?.contains("multipart/form-data") == true ||
            usingAppsSignature == "true") {
            return SecurityUtil.signWithPayload(apps, privateKey)
        }

        val body = minifyRequestBody(requestCopy.body)
        if (body.isNotEmpty()) {
            return SecurityUtil.signWithPayload(body, privateKey)
        }

        return SecurityUtil.signWithPayload(defaultPayload, privateKey)
    }

    private fun minifyRequestBody(body: RequestBody?): String {
        return try {
            val buffer = Buffer()
            if (body == null) {
                return ""
            }
            body.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            ""
        }
    }
}