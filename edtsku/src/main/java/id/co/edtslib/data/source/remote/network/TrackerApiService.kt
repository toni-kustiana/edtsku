package id.co.edtslib.data.source.remote.network

import id.co.edtslib.domain.model.TrackerDataList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TrackerApiService {

    @POST("idmapps_tracker_gateway")
    suspend fun sendTracks(@Body track: TrackerDataList): Response<String>

}