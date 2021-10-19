package com.example.bookclub.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.example.*
import com.example.bookclub.database.Database
import com.example.bookclub.models.SearchItem
import com.example.bookclub.models.UserWithSearchItems
import com.example.type.BookInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository_Impl(
    private val apolloClient: ApolloClient,
    private val db: Database
): AuthRepository {

    override suspend fun apiServiceSearch(searchTerm: String, route: String): Response<ApiServiceSearchQuery.Data> {
        return withContext(Dispatchers.IO){
            apolloClient.query(ApiServiceSearchQuery(searchTerm, route)).await()
        }
    }

    override suspend fun findByISBN(isbn: String): Response<FindByIsbnQuery.Data> {
        return withContext(Dispatchers.IO){
            apolloClient.query(FindByIsbnQuery(isbn)).await()
        }
    }

    override suspend fun getUserBooks(): Response<UserBooksQuery.Data> {
        return withContext(Dispatchers.IO){
            apolloClient.query(UserBooksQuery()).await()
        }
    }

    override suspend fun refreshAccessToken(): Response<RefreshAccessTokenMutation.Data> {
        return withContext(Dispatchers.IO){
            apolloClient.mutate(RefreshAccessTokenMutation()).await()
        }
    }

    override suspend fun getUserRecentSearchItems(userId: String): UserWithSearchItems {
        return withContext(Dispatchers.IO){
            db.searchItemDao.userSearchItems(userId)
        }
    }

    override suspend fun addUserRecentSearch(searchItem: SearchItem) {
        return withContext(Dispatchers.IO){
            db.searchItemDao.insertSearchItem(searchItem)
        }
    }

    override suspend fun deleteSearchItem(searchItem: SearchItem) {
        withContext(Dispatchers.IO){
            db.searchItemDao.deleteSearchItem(searchItem)
        }
    }

    override suspend fun addToReadingList(book: BookInput): Response<AddToReadingListMutation.Data> {
        return withContext(Dispatchers.IO){
            apolloClient.mutate(AddToReadingListMutation(book)).await()
        }
    }

    override suspend fun doesUserBookExist(isbn: String): Response<DoesUserBookExistQuery.Data> {
        return withContext(Dispatchers.IO){
            apolloClient.query(DoesUserBookExistQuery(isbn)).await()
        }
    }

}