package id.co.edtslib.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

class SafeOkHttpClient {

    fun get(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = if (EdtsKu.debugging) HttpLoggingInterceptor.Level.BODY else
                HttpLoggingInterceptor.Level.NONE }

        return try {
            val builder = OkHttpClient.Builder()
            if (EdtsKu.sslPinner.isNotEmpty() && EdtsKu.sslDomain.isNotEmpty()) {
                val certificatePinner = CertificatePinner.Builder()
                    .add(
                        EdtsKu.sslDomain,
                        EdtsKu.sslPinner
                    )
                    .build()
                builder.certificatePinner(certificatePinner)
            }

            builder.addInterceptor(interceptor)
            builder.addNetworkInterceptor(StethoInterceptor())

            if (EdtsKu.trustManagerFactory != null) {
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, EdtsKu.trustManagerFactory!!.trustManagers, SecureRandom())

                val sslSocketFactory = sslContext.socketFactory
                builder.sslSocketFactory(sslSocketFactory, EdtsKu.trustManagerFactory!!.trustManagers[0] as X509TrustManager)
            }

            builder.build()
        } catch (e: Exception) {
            OkHttpClient.Builder().addInterceptor(interceptor)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        }
    }
}