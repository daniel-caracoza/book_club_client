package com.example.bookclub.repository

import androidx.lifecycle.LiveData
import com.apollographql.apollo.api.Response
import com.example.*
import com.example.bookclub.models.SearchItem
import com.example.bookclub.models.UserWithSearchItems
import com.example.type.BookInput

interface AuthRepository {

    suspend fun apiServiceSearch(searchTerm: String, route: String): Response<ApiServiceSearchQuery.Data>

    suspend fun findByISBN(isbn: String): Response<FindByIsbnQuery.Data>

    suspend fun getUserBooks(): Response<UserBooksQuery.Data>

    suspend fun refreshAccessToken(): Response<RefreshAccessTokenMutation.Data>

    suspend fun getUserRecentSearchItems(userId:String): UserWithSearchItems

    suspend fun addUserRecentSearch(searchItem: SearchItem)

    suspend fun deleteSearchItem(searchItem: SearchItem)

    suspend fun addToReadingList(book: BookInput): Response<AddToReadingListMutation.Data>

    suspend fun doesUserBookExist(isbn: String): Response<DoesUserBookExistQuery.Data>


}