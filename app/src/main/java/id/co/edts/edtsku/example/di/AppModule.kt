package id.co.edts.edtsku.example.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single { provideApiService(get(named("edtsku"))) }
}

private fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)