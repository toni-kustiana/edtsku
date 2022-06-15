package id.co.edtslib.domain.interactor

import id.co.edtslib.domain.model.DownloadResult
import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    fun download(url: String): Flow<DownloadResult?>

}