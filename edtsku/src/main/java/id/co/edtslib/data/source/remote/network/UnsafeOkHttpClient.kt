package id.co.edtslib.data.source.remote.network

import android.annotation.SuppressLint
import com.facebook.stetho.okhttp3.StethoInterceptor
import id.co.edtslib.EdtsKu
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class UnsafeOkHttpClient {
    @SuppressLint("CustomX509TrustManager")
    fun get(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply { level = if (EdtsKu.debugging)
            HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE }
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @SuppressLint("TrustAllX509TrustManager")
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    @SuppressLint("TrustAllX509TrustManager")
                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )
            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _: String?, _: SSLSession? -> true }
            builder.addInterceptor(interceptor)
            builder.addNetworkInterceptor(StethoInterceptor())

            if (EdtsKu.timeout != null) {
                builder.callTimeout(EdtsKu.timeout!!, TimeUnit.SECONDS)
                builder.connectTimeout(EdtsKu.timeout!!, TimeUnit.SECONDS)
                builder.readTimeout(EdtsKu.timeout!!, TimeUnit.SECONDS)
            }

            builder.build()
        } catch (e: Exception) {
            val builder = OkHttpClient.Builder()
            if (EdtsKu.timeout != null) {
                builder.callTimeout(EdtsKu.timeout!!, TimeUnit.SECONDS)
                builder.connectTimeout(EdtsKu.timeout!!, TimeUnit.SECONDS)
                builder.readTimeout(EdtsKu.timeout!!, TimeUnit.SECONDS)
            }
            builder.addInterceptor(interceptor)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        }
    }
}