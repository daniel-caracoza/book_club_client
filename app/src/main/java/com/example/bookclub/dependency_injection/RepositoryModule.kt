package com.example.bookclub.dependency_injection

import com.apollographql.apollo.ApolloClient
import com.example.bookclub.repository.Repository
import com.example.bookclub.repository.Repository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepository(
        @NonAuthApolloClient apolloClient: ApolloClient
    ): Repository {
        return Repository_Impl(apolloClient)
    }
}