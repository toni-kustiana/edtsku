package id.co.edtslib.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class SafeOkHttpClient {

    fun get(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = if (EdtsKu.debugging) HttpLoggingInterceptor.Level.BODY else
                HttpLoggingInterceptor.Level.NONE }

        return try {
            val certificatePinner = CertificatePinner.Builder()
                .add(
                    EdtsKu.sslDomain,
                    EdtsKu.sslPinner
                )
                .build()

            val builder = OkHttpClient.Builder()
            builder.certificatePinner(certificatePinner)
            builder.addInterceptor(interceptor)
            builder.addNetworkInterceptor(StethoInterceptor())

            builder.build()
        } catch (e: Exception) {
            OkHttpClient.Builder().addInterceptor(interceptor)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        }
    }
}