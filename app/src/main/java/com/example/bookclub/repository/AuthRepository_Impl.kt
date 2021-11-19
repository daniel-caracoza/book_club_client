package com.example.bookclub.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.example.*
import com.example.bookclub.data.ApiPagingSource
import com.example.bookclub.database.Database
import com.example.bookclub.models.SearchItem
import com.example.bookclub.models.SearchResultItem
import com.example.bookclub.models.UserWithSearchItems
import com.example.type.BookInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AuthRepository_Impl(
    private val apolloClient: ApolloClient,
    private val db: Database
): AuthRepository {

    override suspend fun createClub(clubId: String, clubName: String, clubImage: String, clubBookTitle:String, clubBookAuthor: String): Response<CreateClubMutation.Data> {
        return withContext(Dispatchers.IO) {
            apolloClient.mutate(CreateClubMutation(clubId, clubName, clubImage, clubBookTitle, clubBookAuthor)).await()
        }
    }

    override suspend fun getBooksForClubCreation(): Response<UserBooksForClubCreationQuery.Data> {
        return withContext(Dispatchers.IO) {
            apolloClient.query(UserBooksForClubCreationQuery()).await()
        }
    }

    override suspend fun getUserClubs(): Response<GetClubsQuery.Data> {
        return withContext(Dispatchers.IO){
            apolloClient.query(GetClubsQuery()).await()
        }
    }

    override fun apiServiceSearch(searchTerm: String, route: String): Flow<PagingData<SearchResultItem>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { ApiPagingSource(apolloClient, searchTerm, route)}
        ).flow
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