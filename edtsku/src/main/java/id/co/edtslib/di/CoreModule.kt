package id.co.edtslib.di

import android.app.Application
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.securepreferences.SecurePreferences
import id.co.edtslib.BuildConfig
import id.co.edtslib.EdtsKu
import id.co.edtslib.data.source.remote.network.AuthInterceptor
import id.co.edtslib.data.source.remote.network.SafeOkHttpClient
import id.co.edtslib.data.source.remote.network.UnsafeOkHttpClient
import id.co.edtslib.data.source.local.HttpHeaderLocalSource
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

val networkingModule = module {
    single { provideOkHttpClient() }
    single { provideGson() }
    single { provideGsonConverterFactory(get()) }

    single(named("api")) { provideRetrofit(get(), get(), get(), get()) }
    single(named("mainApi")) { provideRetrofit(get(), get(), get(), get()) }
    single(named("authApi")) { provideRetrofit(get(), get(), get(), get()) }
}

val sharedPreferencesModule = module {
    single {
        if (EdtsKu.debugging) {
            PreferenceManager.getDefaultSharedPreferences(androidContext())
        } else {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val spec = KeyGenParameterSpec.Builder(
                        MasterKey.DEFAULT_MASTER_KEY_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
                        .build()
                    val masterKey = MasterKey.Builder(get())
                        .setKeyGenParameterSpec(spec)
                        .build()

                    EncryptedSharedPreferences.create(
                        androidContext(),
                        "edtsku_secret_shared_prefs",
                        masterKey,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                    )
                } else {
                    SecurePreferences(
                        androidContext(),
                        BuildConfig.DB_PASS,
                        "edtsku_secret_shared_prefs"
                    )
                }
            } catch (e: Exception) {
                PreferenceManager.getDefaultSharedPreferences(androidContext())
            } catch (e: NoClassDefFoundError) {
                PreferenceManager.getDefaultSharedPreferences(androidContext())
            }
        }
    }
}

private fun provideOkHttpClient(): OkHttpClient =
    if ((EdtsKu.sslDomain.isNotEmpty() && EdtsKu.sslPinner.isNotEmpty())
        || EdtsKu.trustManagerFactory != null
    )
        SafeOkHttpClient().get() else UnsafeOkHttpClient().get()

private fun provideGson(): Gson = Gson()

private fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
    GsonConverterFactory.create(gson)

private fun provideRetrofit(
    app: Application,
    okHttpClient: OkHttpClient,
    converterFactory: GsonConverterFactory,
    httpHeaderLocalSource: HttpHeaderLocalSource
): Retrofit {
    val apps = Gson().toJson(TrackerApps.create(app.applicationContext))
    return Retrofit.Builder()
        .baseUrl(EdtsKu.baseUrlApi)
        .client(
            okHttpClient.newBuilder().addInterceptor(
                AuthInterceptor(httpHeaderLocalSource = httpHeaderLocalSource,
                    apps = apps,
                    privateKeyFileContent = EdtsKu.privateKeyFileContent,
                    defaultPayload = EdtsKu.defaultPayload,
                    enableSignature = EdtsKu.enableSignature)
            ).build()
        )
        .addConverterFactory(converterFactory)
        .build()

}
