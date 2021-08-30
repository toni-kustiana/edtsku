package mypoin.indomaret.android.data.cache.tracker

import com.google.gson.annotations.SerializedName
import java.util.*

data class TrackerPageDetailCore (
    @SerializedName("event_name")
    val eventName: String,
    @SerializedName("event_timestamp")
    val eventTimeStamp: Long,
    @SerializedName("page_name")
    val pageName: String,
    val details: Any?
) {
    companion object {
        fun create(screeName: String, details: Any?) =
            TrackerPageDetailCore("page_detail", Date().time, screeName, details)
    }
}