package id.co.edts.edtsku.example

import id.co.edts.edtsku.example.data.LocationResponse
import id.co.edtslib.data.source.remote.response.SessionResponse

object DataMapper {

    fun sessionMapResponseToEntity(input: SessionResponse?) =
        input?.let {
            SessionEntity(
                token = input.token,
                refreshToken = input.refreshToken
            )
        }

    fun sessionMapResponseToDomain(input: SessionResponse?) =
        input?.let {
            Session(
                token = input.token,
                refreshToken = input.refreshToken
            )
        }

    fun provinceListMapResponseToDomain(input: List<LocationResponse>?) =
        input?.map {
            Location(
                id = it.id,
                name = it.name
            )
        }

}