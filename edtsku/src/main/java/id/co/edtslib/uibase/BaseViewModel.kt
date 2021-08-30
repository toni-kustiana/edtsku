package id.co.edtslib.uibase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.co.edtslib.domain.usecase.TrackerUseCase

open class BaseViewModel(
    private val trackerUseCase: TrackerUseCase
): ViewModel() {
    fun trackPage(screeName: String) {
        trackerUseCase.sendTrackPage(screeName).asLiveData().observeForever {}
    }
    fun trackApplicationStart() {
        trackerUseCase.sendPageResume().asLiveData().observeForever {  }
    }

    fun trackApplicationExit() {
        trackerUseCase.sendPageExit().asLiveData().observeForever {  }
    }

    fun trackClick(name: String) {
        trackerUseCase.sendTrackClick(name).asLiveData().observeForever {  }
    }
    fun trackSubmission(screeName: String, status: Boolean, reason: String?) {
        trackerUseCase.sendTrackSubmission(screeName, status, reason).asLiveData().observeForever {  }
    }
    fun trackFilters(name: String, filters: List<String>) {
        trackerUseCase.sendTrackerFilter(name, filters).asLiveData().observeForever {  }
    }
    fun trackSort(name: String, sortType: String) {
        trackerUseCase.sendTrackerSort(name, sortType).asLiveData().observeForever {  }
    }
    fun trackPageDetail(screeName: String, details: Any?) {
        trackerUseCase.sendTrackPageDetail(screeName, details).asLiveData().observeForever {  }
    }

    fun addUserImpression(screeName: String, name: String) {
        trackerUseCase.addImpression(screeName, name)
    }

    fun trackUserImpressions(screeName: String) {
        trackerUseCase.sendImpressions(screeName).asLiveData().observeForever {  }
    }

    fun trackFlush() {
        trackerUseCase.flush().asLiveData().observeForever {  }
    }
}