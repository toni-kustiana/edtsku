package id.co.edts.edtsku.example.di

import id.co.edts.edtsku.example.data.ConfigurationInteractor
import id.co.edts.edtsku.example.data.ConfigurationUseCase
import id.co.edts.edtsku.example.data.configuration.ConfigurationRemoteDataSource
import id.co.edts.edtsku.example.data.configuration.ConfigurationRepository
import id.co.edts.edtsku.example.data.configuration.IConfigurationRepository
import okhttp3.internal.connection.ConnectInterceptor
import org.koin.dsl.module

val repositoryModule = module {
    single { ConfigurationRemoteDataSource(get()) }
    single<IConfigurationRepository> { ConfigurationRepository(get()) }
    factory<ConfigurationUseCase> { ConfigurationInteractor(get()) }
}