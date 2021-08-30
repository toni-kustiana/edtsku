package id.co.edtslib.data.source.remote

import id.co.edtslib.data.source.remote.network.TrackerApiService
import id.co.edtslib.data.source.remote.response.BaseDataSource
import id.co.edtslib.domain.model.TrackerList

class TrackerRemoteDataSource(
    private val trackerApiService: TrackerApiService
) : BaseDataSource() {

    suspend fun sendTracks(trackers: TrackerList) =
        getResult { trackerApiService.sendTracks(trackers) }

}