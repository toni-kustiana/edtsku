package id.co.edtslib.di

import id.co.edtslib.data.source.remote.MainRemoteDataSource
import id.co.edtslib.data.source.local.HttpHeaderLocalSource
import id.co.edtslib.domain.repository.IMainRepository
import id.co.edtslib.data.source.MainRepository
import org.koin.dsl.module

val mainRepositoryModule = module {
    single { HttpHeaderLocalSource(get()) }
    single { MainRemoteDataSource(get()) }

    single<IMainRepository> { MainRepository(get(), get()) }
}