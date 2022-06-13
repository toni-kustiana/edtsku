package id.co.edtslib.di

import id.co.edtslib.data.source.MainApiService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val mainAppModule = module {
    single { provideMainApiService(get(named("mainApi"))) }
}

private fun provideMainApiService(retrofit: Retrofit): MainApiService =
    retrofit.create(MainApiService::class.java)