package com.example.bookclub.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.ApiServiceSearchQuery
import com.example.bookclub.models.SearchResultItem
import com.example.bookclub.models.mapToSearchResultItem
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class ApiPagingSource(
    private val apolloClient: ApolloClient,
    private val searchTerm: String,
    private val route: String
): PagingSource<Int, SearchResultItem>() {

    override fun getRefreshKey(state: PagingState<Int, SearchResultItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResultItem> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = apolloClient.query(ApiServiceSearchQuery(searchTerm, route, position)).await()
            val searchResultItems = response.data?.apiServiceSearch?.mapToSearchResultItem()
            val nextKey = if(searchResultItems!!.isEmpty()){
                null
            } else {
                position + (params.loadSize / 20)
            }

            LoadResult.Page(
                data = searchResultItems,
                prevKey = if(position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException){
            LoadResult.Error(exception)
        } catch (exception: ApolloException){
            LoadResult.Error(exception)
        }
    }

}