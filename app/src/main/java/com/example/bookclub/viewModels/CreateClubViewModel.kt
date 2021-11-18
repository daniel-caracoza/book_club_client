package com.example.bookclub.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookclub.models.Club
import com.example.bookclub.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CreateClubViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    fun onCreateClubClick(clubId: String, clubName: String): LiveData<Result<Club>> {
        val mutableLiveData = MutableLiveData<Result<Club>>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = authRepository.createClub(clubId, clubName)
            val club = response.data?.createClub
            if(club == null || response.hasErrors()){
                mutableLiveData.postValue(Result.failure(Exception("Unable to create Club! ")))
            } else {
                mutableLiveData.postValue(Result.success(Club(club.id, club.clubName)))
            }
        }
        return mutableLiveData
    }


}