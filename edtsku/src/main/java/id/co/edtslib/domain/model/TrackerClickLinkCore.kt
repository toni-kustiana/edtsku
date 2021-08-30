package id.co.edtslib.domain.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class TrackerClickLinkCore (
    @SerializedName("event_name")
    val eventName: String,
    @SerializedName("event_timestamp")
    val eventTimeStamp: Long,
    @SerializedName("link_label")
    val linkName: String
) {
    companion object {
        fun create(name: String) =
            TrackerClickLinkCore("click_link", Date().time, name)
    }
}