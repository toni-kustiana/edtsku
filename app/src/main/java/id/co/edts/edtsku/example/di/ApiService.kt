package id.co.edts.edtsku.example.di

import id.co.edtslib.data.source.remote.response.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("configuration/api/mobile/contact-us/get-contact-us")
    suspend fun getContactUs(): Response<ApiResponse<Any?>>

}