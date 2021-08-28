package com.example.bookclub.dependency_injection

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.apollographql.apollo.exception.ApolloNetworkException
import com.example.bookclub.repository.AuthRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class RefreshTokenCoroutineWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
): CoroutineWorker(appContext, workerParams) {
    @Inject
    lateinit var authRepository: AuthRepository
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override suspend fun doWork(): Result {
        return try {
            val mutationData = authRepository.refreshAccessToken()
            val accessToken = mutationData.data?.refreshAccessToken?.accessToken
            Log.d("RefreshToken", accessToken.toString())
            with(sharedPreferences.edit()){
                putString("token", accessToken)
                apply()
            }
            Result.success()
        } catch (error: ApolloNetworkException){
            Log.e("RefreshToken", error.toString())
            Result.failure()
        }
    }

}