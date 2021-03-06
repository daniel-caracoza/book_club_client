package com.example.bookclub.repository

import androidx.lifecycle.LiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.example.ApiServiceSearchQuery
import com.example.LoginMutation
import com.example.UserBooksQuery
import com.example.bookclub.database.Database
import com.example.bookclub.models.DatabaseBook
import com.example.bookclub.models.UsersBooks
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

    override suspend fun getUserBooks(): Response<UserBooksQuery.Data> {
        return withContext(Dispatchers.IO){
            apolloClient.query(UserBooksQuery()).await()
        }
    }


//    override suspend fun getUserBooks(userId:Int): LiveData<List<UsersBooks>> {
//        return withContext(Dispatchers.IO){
//            db.databaseBookDao.getUserBooks(userId)
//        }
//    }

}