package id.co.edtslib.domain.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class TrackerFilterCore (
    @SerializedName("event_name")
    val eventName: String,
    @SerializedName("event_timestamp")
    val eventTimeStamp: Long,
    @SerializedName("page_name")
    val pageName: String,
    @SerializedName("filter_list")
    val list: List<String>
) {
    companion object {
        fun create(pageName: String, list: List<String>) =
            TrackerFilterCore("user_filter", Date().time, pageName,
                list)


    }

}