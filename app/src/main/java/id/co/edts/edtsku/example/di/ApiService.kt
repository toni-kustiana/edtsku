package id.co.edts.edtsku.example.di

import id.co.edts.edtsku.example.data.LocationResponse
import id.co.edts.edtsku.example.data.LoginResponse
import id.co.edtslib.data.source.remote.response.ApiContentResponse
import id.co.edtslib.data.source.remote.response.ApiResponse
import id.co.edtslib.data.source.remote.response.SessionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @POST("/api/app/authenticate")
    suspend fun login(@Body request: LoginRequest)
            : Response<ApiResponse<LoginResponse?>>

    @Headers("pathSignature: 1")
    @POST("/customer/api/mobile/user/login-visitor")
    suspend fun loginVisitor(): Response<ApiResponse<SessionResponse>>

    @GET("/catalog/api/mobile/location/get-provinsi")
    suspend fun getProvinces(): Response<ApiContentResponse<List<LocationResponse>>>

}