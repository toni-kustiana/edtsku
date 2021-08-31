package id.co.edtslib.di

import id.co.edtslib.data.source.TrackerRepository
import id.co.edtslib.data.source.local.TrackerLocalDataSource
import id.co.edtslib.data.source.remote.TrackerRemoteDataSource
import id.co.edtslib.domain.repository.ITrackerRepository
import id.co.edtslib.domain.repository.HttpHeaderLocalSource
import org.koin.dsl.module

val mainRepositoryModule = module {
    single { TrackerRemoteDataSource(get()) }
    single { TrackerLocalDataSource(get(), get()) }
    single { HttpHeaderLocalSource(get()) }

    single<ITrackerRepository> { TrackerRepository(get(), get(), get()) }
}