package id.co.edtslib.di

import id.co.edtslib.uibase.BaseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainViewModel = module {
    viewModel { BaseViewModel() }
}
