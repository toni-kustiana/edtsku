package id.co.edts.edtsku.example.di

import id.co.edts.edtsku.example.data.LoginResponse
import id.co.edtslib.domain.model.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/app/authenticate")
    suspend fun login(@Body request: LoginRequest)
            : Response<ApiResponse<LoginResponse?>>

}