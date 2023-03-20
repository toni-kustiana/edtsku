package id.co.edtslib.domain.interactor

import id.co.edtslib.domain.repository.IMainRepository

class MainInteractor(private val repository: IMainRepository): MainUseCase {
    override fun download(url: String) =
        repository.download(url)

    override fun getToken() =
        repository.getToken()
}