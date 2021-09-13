package id.co.edts.edtsku.example.di

import id.co.edts.edtsku.example.ConfigurationViewModel
import id.co.edtslib.uibase.BaseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ConfigurationViewModel(get()) }
}