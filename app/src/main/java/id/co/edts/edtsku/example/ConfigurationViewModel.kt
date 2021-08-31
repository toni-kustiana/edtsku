package id.co.edts.edtsku.example

import androidx.lifecycle.asLiveData
import id.co.edts.edtsku.example.data.ConfigurationUseCase
import id.co.edtslib.domain.usecase.tracker.TrackerUseCase
import id.co.edtslib.uibase.BaseViewModel

class ConfigurationViewModel(private val configurationUseCase: ConfigurationUseCase,
    trackerUseCase: TrackerUseCase
): BaseViewModel(trackerUseCase) {
    fun getContactUs() = configurationUseCase.getContactUs().asLiveData()
}