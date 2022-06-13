package id.co.edtslib.domain.interactor

import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    fun download(url: String): Flow<String?>

}