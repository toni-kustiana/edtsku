package id.co.edtslib.di

import id.co.edtslib.data.source.remote.network.AuthApiService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val sessionModule = module {
    single { provideAuthApiService(get(named("authApi"))) }
}

private fun provideAuthApiService(retrofit: Retrofit) =
    retrofit.create(AuthApiService::class.java)