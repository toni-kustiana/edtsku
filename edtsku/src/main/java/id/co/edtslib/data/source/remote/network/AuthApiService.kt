package id.co.edtslib.data.source.remote.network

import id.co.edtslib.data.source.remote.request.SessionRequest
import id.co.edtslib.data.source.remote.response.ApiResponse
import id.co.edtslib.data.source.remote.response.SessionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApiService {

    @POST("{path}")
    suspend fun refreshToken(
        @Path("path", encoded = true) path: String,
        @Body request: SessionRequest
    ): Response<ApiResponse<SessionResponse>>

}