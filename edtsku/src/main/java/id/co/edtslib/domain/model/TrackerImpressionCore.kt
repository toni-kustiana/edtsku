package mypoin.indomaret.android.data.cache.tracker

import com.google.gson.annotations.SerializedName

data class TrackerImpressionCore (
    @SerializedName("event_name")
    val eventName: String,
    @SerializedName("event_timestamp")
    val eventTimeStamp: Long,
    @SerializedName("page_name")
    val pageName: String,
    @SerializedName("impression_list")
    val impressionList: MutableList<String>
) {
    companion object {
        fun create(timeStamp: Long, screeName: String, list: MutableList<String>) =
            TrackerImpressionCore("user_impression", timeStamp, screeName, list)
    }
}