package mypoin.indomaret.android.data.cache.tracker

import com.google.gson.annotations.SerializedName
import java.util.*

data class TrackerSubmissionCore (
    @SerializedName("event_name")
    val eventName: String,
    @SerializedName("event_timestamp")
    val eventTimeStamp: Long,
    @SerializedName("event_label")
    val eventLabel: String,
    @SerializedName("event_status")
    val eventStatus: String,
    @SerializedName("failed_reason")
    val eventFailedReason: String?
) {
    companion object {
        fun create(screeName: String, status: Boolean, reason: String?) =
            TrackerSubmissionCore("event_submission", Date().time,  screeName,
                if (status) "success" else "failed", reason)
    }
}