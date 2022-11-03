package id.co.edtslib.di

import id.co.edtslib.data.source.remote.SessionRemoteDataSource
import org.koin.dsl.module

val sessionRepositoryModule = module {
    single { SessionRemoteDataSource(get()) }
}