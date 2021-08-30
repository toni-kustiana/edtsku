package mypoin.indomaret.android.data.cache.tracker

import com.google.gson.annotations.SerializedName
import java.util.*

data class TrackerSortCore (
    @SerializedName("event_name")
    val eventName: String,
    @SerializedName("event_timestamp")
    val eventTimeStamp: Long,
    @SerializedName("page_name")
    val pageName: String,
    @SerializedName("sort_type")
    val sortType: String
) {
    companion object {
        fun create(pageName: String, sortType: String) =
            TrackerSortCore("user_sort", Date().time, pageName,
                sortType)


    }

}