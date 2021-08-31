package com.example.bookclub.repository

import androidx.lifecycle.LiveData
import com.apollographql.apollo.api.Response
import com.example.ApiServiceSearchQuery
import com.example.RefreshAccessTokenMutation
import com.example.UserBooksQuery
import com.example.bookclub.models.DatabaseBook
import com.example.bookclub.models.SearchItem
import com.example.bookclub.models.UserWithSearchItems
import com.example.bookclub.models.UsersBooks

interface AuthRepository {

    suspend fun apiServiceSearch(searchTerm: String, route: String): Response<ApiServiceSearchQuery.Data>

    suspend fun getUserBooks(): Response<UserBooksQuery.Data>

    suspend fun refreshAccessToken(): Response<RefreshAccessTokenMutation.Data>

    suspend fun getUserRecentSearchItems(): UserWithSearchItems

    suspend fun deleteSearchItem(searchItem: SearchItem)

    //suspend fun getUserBooks(userId: Int): LiveData<List<UsersBooks>>

}