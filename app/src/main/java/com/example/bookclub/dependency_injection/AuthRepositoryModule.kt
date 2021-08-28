package com.example.bookclub.dependency_injection

import com.apollographql.apollo.ApolloClient
import com.example.bookclub.database.Database
import com.example.bookclub.repository.AuthRepository
import com.example.bookclub.repository.AuthRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthRepositoryModule {

    @Provides
    @Singleton()
    fun provideAuthRepository(
        @AuthApolloClient authApolloClient: ApolloClient,
        db: Database
    ): AuthRepository {
        return AuthRepository_Impl(authApolloClient, db)
    }
}