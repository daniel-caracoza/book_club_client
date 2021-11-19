package com.example.bookclub.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookclub.models.Club
import com.example.bookclub.models.mapToDomainClub
import com.example.bookclub.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubsViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    fun refreshUserClubs(): StateFlow<Result<List<Club>>> {
        val mutableStateFlow = MutableStateFlow<Result<List<Club>>>(Result.success(emptyList()))
        viewModelScope.launch {
            mutableStateFlow.value = runCatching {
                val response = authRepository.getUserClubs()
                val clubs = response.data?.getClubs ?: return@launch
                clubs.mapToDomainClub()
            }
        }
        return mutableStateFlow
    }

}

