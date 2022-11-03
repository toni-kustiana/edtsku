package id.co.edtslib.data.source.remote.response

import com.google.gson.annotations.SerializedName

class PageableDataResponse (
    @SerializedName("pageNumber")
    val pageNumber: Int,
    @SerializedName("pageSize")
    val pageSize: Int
)