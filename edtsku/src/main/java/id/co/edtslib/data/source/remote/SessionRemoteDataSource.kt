package id.co.edtslib.data.source.remote

import id.co.edtslib.EdtsKu
import id.co.edtslib.data.BaseDataSource
import id.co.edtslib.data.source.remote.network.AuthApiService
import id.co.edtslib.data.source.remote.request.SessionRequest

class SessionRemoteDataSource(
    private val authApiService: AuthApiService
) : BaseDataSource() {

    suspend fun refreshToken(refreshToken: String) =
        getResult {
            authApiService.refreshToken(
                EdtsKu.refreshTokenUrlApi,
                SessionRequest(
                    refreshToken = refreshToken
                )
            )
        }

}