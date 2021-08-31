package id.co.edtslib.domain.model

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName

data class TrackerUser (
    @SerializedName("session_id")
    val sessionId: String?,
    @SerializedName("user_id")
    val userId: Long?,
    @SerializedName("user_ip_address")
    val userIpAddress: String?
) {
    /*
    companion object {
        @SuppressLint("HardwareIds")

        fun create(sessionId: String?, customerLocalDataSource: CustomerLocalDataSource) : TrackerUser {
            val session = customerLocalDataSource.getCached()
            return TrackerUser(
                sessionId, session?.userId,
                ConnectivityUtil.getIPAddress(true)
            )
        }
    }*/
}