package mypoin.indomaret.android.data.cache.tracker

import com.google.gson.annotations.SerializedName
import java.util.*

data class TrackerActivityCore (
    @SerializedName("event_name")
    val eventName: String,
    @SerializedName("event_timestamp")
    val eventTimeStamp: Long,
    @SerializedName("activity_details")
    val activityDetails: String
) {
    companion object {
        fun createPageResume() =
            TrackerActivityCore("app_activity", Date().time, "resume_app")

        fun createPageExit() =
            TrackerActivityCore("app_activity", Date().time, "exit_app")

    }

}