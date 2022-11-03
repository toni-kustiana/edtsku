package id.co.edtslib.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class SessionRequest(
    @field:SerializedName("refreshToken")
    val refreshToken: String? = null
)