package id.co.edtslib.domain.model

data class ContentResponse<T> (
    val content: T?,
    val pageable: PageableData?,
    val totalPages: Int?,
    val additionalData: Map<String, Any>?
) {
    fun getTotalPages() = totalPages ?: 0
    fun getPageNumber() = pageable?.pageNumber ?: 0
    private fun getPageSize(): Int = pageable?.pageSize ?: 0
    fun getOffset() = getPageSize()*getPageNumber()
}