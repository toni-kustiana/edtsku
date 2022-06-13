package id.co.edtslib.di

import id.co.edtslib.domain.repository.MainRemoteDataSource
import id.co.edtslib.domain.repository.HttpHeaderLocalSource
import id.co.edtslib.domain.repository.IMainRepository
import id.co.edtslib.domain.repository.MainRepository
import org.koin.dsl.module

val mainRepositoryModule = module {
    single { HttpHeaderLocalSource(get()) }
    single { MainRemoteDataSource(get()) }

    single<IMainRepository> { MainRepository(get(), get()) }
}