package com.example.bookclub.dependency_injection

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthApolloClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NonAuthApolloClient

@Module
@InstallIn(SingletonComponent::class)
object ApolloModule {

    @Singleton
    @AuthApolloClient
    @Provides
    fun provideAuthClient(authOkHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.builder()
            .serverUrl("http://10.0.0.93:4000/graphql")
            .okHttpClient(authOkHttpClient)
            .build()
    }

    @NonAuthApolloClient
    @Provides
    fun provideClient(): ApolloClient {
        return ApolloClient.builder()
            .serverUrl("http://10.0.0.93:4000/graphql")
            .build()
    }
}

