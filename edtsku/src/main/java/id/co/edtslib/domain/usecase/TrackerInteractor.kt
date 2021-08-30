package id.co.edtslib.domain.usecase

import id.co.edtslib.domain.repository.ITrackerRepository
import kotlinx.coroutines.flow.Flow

class TrackerInteractor(
    private val trackerRepository: ITrackerRepository
) : TrackerUseCase {
    override fun sendTrackPage(name: String) = trackerRepository.sendTrackPage(name)
    override fun sendTrackClick(name: String) = trackerRepository.sendTrackClick(name)
    override fun sendTrackSubmission(
        screeName: String,
        status: Boolean,
        reason: String?
    ) = trackerRepository.sendTrackSubmission(screeName, status, reason)

    override fun sendPageResume() = trackerRepository.sendPageResume()

    override fun sendPageExit() = trackerRepository.sendPageExit()

    override fun sendTrackerFilter(pageName: String, list: List<String>) =
        trackerRepository.sendTrackerFilter(pageName, list)

    override fun sendTrackerSort(pageName: String, sortType: String) =
        trackerRepository.sendTrackerSort(pageName, sortType)

    override fun sendTrackPageDetail(screeName: String, details: Any?) =
        trackerRepository.sendTrackPageDetail(screeName, details)

    override fun sendTrackPageDetailImmediately(screeName: String, details: Any?) =
        trackerRepository.sendTrackPageDetailImmediately(screeName, details)

    override fun flush(): Flow<Boolean> = trackerRepository.flush()

    override fun addImpression(screeName: String, name: String) =
        trackerRepository.addImpression(screeName, name)

    override fun sendImpressions(screeName: String) = trackerRepository.sendImpressions(screeName)
}