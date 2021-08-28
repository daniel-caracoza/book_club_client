package com.example.bookclub.dependency_injection

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.Response
import java.net.CookieManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideAuthInterceptorOkhttpClient(
        sp: SharedPreferences
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor(sp))
            .cookieJar(JavaNetCookieJar(CookieManager()))
            .build()
    }

    private class AuthorizationInterceptor(val sp: SharedPreferences): Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val token = sp.getString("token", "")
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "bearer $token")
                .build()

            return chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideEncryptedSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
        val sharedPrefsFile: String = "secret_data"
        return EncryptedSharedPreferences.create(
            sharedPrefsFile,
            mainKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}