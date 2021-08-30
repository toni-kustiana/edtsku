package id.co.edtslib.data.source.remote.network

import mypoin.indomaret.android.BuildConfig
import mypoin.indomaret.android.data.cache.tracker.TrackerDatas
import mypoin.indomaret.android.util.CommonUtil
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TrackerApiService {

    @POST("idmapps_tracker_gateway")
    suspend fun sendTracks(@Body track: TrackerDatas): Response<String>

}