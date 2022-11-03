package id.co.edtslib.data.source.remote

import id.co.edtslib.data.source.remote.network.MainApiService

class MainRemoteDataSource(private val apiService: MainApiService) {

    suspend fun download(url: String) = apiService.download(url)
}