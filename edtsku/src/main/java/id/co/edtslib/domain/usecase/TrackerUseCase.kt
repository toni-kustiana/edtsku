package id.co.edtslib.domain.usecase

import id.co.edtslib.domain.model.TrackerResponse
import kotlinx.coroutines.flow.Flow

interface TrackerUseCase {
    fun sendTrackPage(name: String): Flow<TrackerResponse>
    fun sendTrackClick(name: String): Flow<TrackerResponse>
    fun sendTrackSubmission(screeName: String, status: Boolean, reason: String?): Flow<TrackerResponse>
    fun sendPageResume(): Flow<TrackerResponse>
    fun sendPageExit(): Flow<Boolean>
    fun sendTrackerFilter(pageName: String, list: List<String>): Flow<TrackerResponse>
    fun sendTrackerSort(pageName: String, sortType: String): Flow<TrackerResponse>
    fun sendTrackPageDetail(screeName: String, details: Any?): Flow<TrackerResponse>
    fun sendTrackPageDetailImmediately(screeName: String, details: Any?)
    fun flush(): Flow<Boolean>
    fun addImpression(screeName: String, name: String)
    fun sendImpressions(screeName: String): Flow<String?>
}