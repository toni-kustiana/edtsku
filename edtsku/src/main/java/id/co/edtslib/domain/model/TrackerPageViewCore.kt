package id.co.edtslib.domain.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class TrackerPageViewCore (
    @SerializedName("event_name")
    val eventName: String,
    @SerializedName("event_timestamp")
    val eventTimeStamp: Long,
    @SerializedName("page_urlpath")
    val pageUrlPath: String,
    @SerializedName("page_name")
    val pageName: String
) {
    companion object {
        fun create(screeName: String) =
            TrackerPageViewCore("page_view", Date().time, "", screeName)
    }
}