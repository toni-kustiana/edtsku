package id.co.edtslib.di

import id.co.edtslib.domain.interactor.MainInteractor
import id.co.edtslib.domain.interactor.MainUseCase
import org.koin.dsl.module

val mainInteractorModule = module {
    factory<MainUseCase> { MainInteractor(get()) }

}