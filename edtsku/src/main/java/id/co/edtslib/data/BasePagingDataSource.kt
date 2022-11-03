package id.co.edtslib.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.co.edtslib.data.source.remote.response.ApiPagingResponse
import retrofit2.Response

class BasePagingDataSource<T : Any>(private val call: suspend (page: Int) -> Response<ApiPagingResponse<T>>): PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>) = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = call(currentLoadingPageKey)
            val responseData = mutableListOf<T>()
            val data = response.body()?.data?.content ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}