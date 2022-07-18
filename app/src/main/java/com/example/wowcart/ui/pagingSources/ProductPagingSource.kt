package com.example.wowcart.ui.pagingSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wowcart.data.ProductApiService
import com.example.wowcart.data.dto.ProductDTO

class ProductPagingSource(private val service: ProductApiService) :
    PagingSource<Int, ProductDTO>() {
    override fun getRefreshKey(state: PagingState<Int, ProductDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductDTO> {
        val start = params.key ?: 0
        val response = service.getProducts()
        return LoadResult.Page(
            data = response.results,
            prevKey = when (start) {
                0 -> null
                else -> response.currentPage - 1
            },
            nextKey = response.currentPage + 1
        )
    }

}