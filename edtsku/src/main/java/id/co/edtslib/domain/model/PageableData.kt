package id.co.edtslib.domain.model

import com.google.gson.annotations.SerializedName

class PageableData (
    @SerializedName("pageNumber")
    val pageNumber: Int,
    @SerializedName("pageSize")
    val pageSize: Int
)