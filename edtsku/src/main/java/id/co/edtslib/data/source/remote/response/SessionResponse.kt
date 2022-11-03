package id.co.edtslib.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SessionResponse(
    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("refreshToken")
    val refreshToken: String
)
