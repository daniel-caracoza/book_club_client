package com.example.bookclub.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookclub.models.ClubTopic
import com.example.bookclub.models.mapToDomainClubTopic
import com.example.bookclub.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ClubTopicsViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    fun getTopics(clubId: String): StateFlow<Result<List<ClubTopic>>> {
        val topics = MutableStateFlow<Result<List<ClubTopic>>>(Result.success(emptyList()))
        viewModelScope.launch {
            val response = authRepository.getClubTopics(clubId)
            val clubTopics = response.data?.clubTopics
            if(clubTopics == null || response.hasErrors()){
                topics.value = Result.failure(Exception("Unable to load topics!"))
                return@launch
            }
            topics.value = Result.success(clubTopics.mapToDomainClubTopic())
        }
        return topics
    }
}