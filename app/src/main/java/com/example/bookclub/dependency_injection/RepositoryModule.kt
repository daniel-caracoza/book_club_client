package com.example.bookclub.dependency_injection

import com.apollographql.apollo.ApolloClient
import com.example.bookclub.database.Database
import com.example.bookclub.repository.Repository
import com.example.bookclub.repository.Repository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        @AuthApolloClient apolloClient: ApolloClient,
        db: Database
    ): Repository {
        return Repository_Impl(apolloClient, db)
    }

}