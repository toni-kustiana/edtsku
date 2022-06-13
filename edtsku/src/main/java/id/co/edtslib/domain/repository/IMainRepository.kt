package id.co.edtslib.domain.repository

import kotlinx.coroutines.flow.Flow

interface IMainRepository {
    fun download(url: String): Flow<String?>
}