package id.co.edtslib.di

import id.co.edtslib.domain.usecase.tracker.TrackerInteractor
import id.co.edtslib.domain.usecase.tracker.TrackerUseCase
import org.koin.dsl.module

val mainInteractorModule = module {
    factory<TrackerUseCase> { TrackerInteractor(get()) }
}