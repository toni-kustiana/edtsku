package id.co.edts.edtsku.example

import android.content.Context
import id.co.edts.edtsku.example.di.appModule
import id.co.edts.edtsku.example.di.repositoryModule
import id.co.edts.edtsku.example.di.viewModelModule
import id.co.edtslib.di.EdtsStartApplication

class TestProjectStartApplication(
    context: Context
): EdtsStartApplication(context) {
    override fun additionModules() = listOf(appModule, repositoryModule, viewModelModule)
}