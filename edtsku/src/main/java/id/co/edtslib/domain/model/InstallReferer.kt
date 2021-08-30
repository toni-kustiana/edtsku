package id.co.edtslib.domain.model

import com.google.gson.annotations.SerializedName

data class InstallReferer(
    @SerializedName("utm_raw")
    val utm_raw: String?
)
