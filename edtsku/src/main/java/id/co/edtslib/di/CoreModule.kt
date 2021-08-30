package id.co.edtslib.di

import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.securepreferences.SecurePreferences
import id.co.edtslib.util.AndroidUtil
import id.co.edtslib.util.CommonUtil
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkingModule = module {
    single { provideOkHttpClient() }
    single { provideGson() }
    single { provideGsonConverterFactory(get()) }
    single(named("api")) { provideRetrofit(androidContext(), get(), get(), get()) }
}

val sharedPreferencesModule = module {
    single {
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
                get(),
                "secret_shared_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } else {
            SecurePreferences(get())
        }
    }
}

private fun provideOkHttpClient(): OkHttpClient = UnsafeOkHttpClient().get()

private fun provideGson(): Gson = Gson()

private fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
    GsonConverterFactory.create(gson)

private fun provideRetrofit(
    context: Context,
    gson: Gson,
    okHttpClient: OkHttpClient,
    converterFactory: GsonConverterFactory
): Retrofit {
    val json = AndroidUtil.loadJSONFromAsset(context, "edtsku.json")
    val configure =  gson.fromJson<Configure>(json, object : TypeToken<Configure>() {}.type)

    val baseUrl = CommonUtil.hexToAscii(configure.baseUrl)
    val key = if (configure.key == null) null else CommonUtil.hexToAscii(configure.key)

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient.newBuilder().addInterceptor(AuthInterceptor(key)).build())
        .addConverterFactory(converterFactory)
        .build()
}