package com.example.bookclub.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.example.LoginMutation
import com.example.RegisterMutation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository_Impl(
    private val apolloClient: ApolloClient
): Repository {

    override suspend fun registerUser(username: String, email: String, password: String): Response<RegisterMutation.Data> {
        return withContext(Dispatchers.IO){
            apolloClient.mutate(RegisterMutation(username, email, password)).await()
        }
    }

    override suspend fun loginUser(username: String, password: String): Response<LoginMutation.Data> {
        return withContext(Dispatchers.IO){
            apolloClient.mutate(LoginMutation(username, password)).await()
        }
    }

}