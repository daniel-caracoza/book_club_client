package com.example.bookclub.repository

import com.apollographql.apollo.api.Response
import com.example.LoginMutation
import com.example.RegisterMutation

interface Repository {

    suspend fun registerUser(username: String, email: String, password: String): Response<RegisterMutation.Data>

    suspend fun loginUser(username: String, password: String): Response<LoginMutation.Data>

}