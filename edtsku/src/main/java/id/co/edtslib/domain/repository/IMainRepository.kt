package id.co.edtslib.domain.repository

import id.co.edtslib.domain.model.DownloadResult
import kotlinx.coroutines.flow.Flow

interface IMainRepository {
    fun download(url: String): Flow<DownloadResult?>
    fun getToken(): Flow<String?>
}