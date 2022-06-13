package id.co.edtslib.data.source

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface MainApiService {
    @Streaming
    @GET
    suspend fun download(@Url fileUrl:String): Response<ResponseBody>

}