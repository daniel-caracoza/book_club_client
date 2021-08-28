package com.example.bookclub.repository

import androidx.lifecycle.LiveData
import com.apollographql.apollo.api.Response
import com.example.ApiServiceSearchQuery
import com.example.RefreshAccessTokenMutation
import com.example.UserBooksQuery
import com.example.bookclub.models.DatabaseBook
import com.example.bookclub.models.UsersBooks

interface AuthRepository {

    suspend fun apiServiceSearch(searchTerm: String, route: String): Response<ApiServiceSearchQuery.Data>

    suspend fun getUserBooks(): Response<UserBooksQuery.Data>

    suspend fun refreshAccessToken(): Response<RefreshAccessTokenMutation.Data>

    //suspend fun getUserBooks(userId: Int): LiveData<List<UsersBooks>>

}