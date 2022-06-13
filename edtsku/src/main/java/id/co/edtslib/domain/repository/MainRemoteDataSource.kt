package id.co.edtslib.domain.repository

import id.co.edtslib.data.source.MainApiService

class MainRemoteDataSource(private val apiService: MainApiService) {

    suspend fun download(url: String) = apiService.download(url)
}