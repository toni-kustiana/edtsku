package id.co.edtslib.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("data")
    val data: T?,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("timestamp")
    val timeStamp: String
) {
    fun isSuccess() = "00" == status || "01" == status
}
