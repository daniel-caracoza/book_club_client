package com.example.bookclub.repository

import androidx.paging.PagingData
import com.apollographql.apollo.api.Response
import com.example.*
import com.example.bookclub.models.SearchItem
import com.example.bookclub.models.SearchResultItem
import com.example.bookclub.models.UserWithSearchItems
import com.example.type.BookInput
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun createClub(clubId: String, clubName: String, clubImage: String, clubBookTitle:String, clubBookAuthor: String): Response<CreateClubMutation.Data>

    suspend fun getBooksForClubCreation(): Response<UserBooksForClubCreationQuery.Data>

    suspend fun getUserClubs(): Response<GetClubsQuery.Data>

    fun apiServiceSearch(searchTerm: String, route: String): Flow<PagingData<SearchResultItem>>

    suspend fun findByISBN(isbn: String): Response<FindByIsbnQuery.Data>

    suspend fun getUserBooks(): Response<UserBooksQuery.Data>

    suspend fun refreshAccessToken(): Response<RefreshAccessTokenMutation.Data>

    suspend fun getUserRecentSearchItems(userId:String): UserWithSearchItems

    suspend fun addUserRecentSearch(searchItem: SearchItem)

    suspend fun deleteSearchItem(searchItem: SearchItem)

    suspend fun addToReadingList(book: BookInput): Response<AddToReadingListMutation.Data>

    suspend fun doesUserBookExist(isbn: String): Response<DoesUserBookExistQuery.Data>


}