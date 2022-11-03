package id.co.edtslib.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ContentResponse<T> (
    @SerializedName("content")
    val content: T?,
    @SerializedName("pageable")
    val pageable: PageableDataResponse?,
    @SerializedName("totalPages")
    val totalPages: Int?,
    @SerializedName("additionalData")
    val additionalData: Map<String, Any>?
) {
    fun getTotalPages() = totalPages ?: 0
    fun getPageNumber() = pageable?.pageNumber ?: 0
    private fun getPageSize(): Int = pageable?.pageSize ?: 0
    fun getOffset() = getPageSize()*getPageNumber()
}