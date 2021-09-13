package id.co.edtslib.di

import id.co.edtslib.domain.repository.HttpHeaderLocalSource
import org.koin.dsl.module

val mainRepositoryModule = module {
    single { HttpHeaderLocalSource(get()) }
}