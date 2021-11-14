package com.example.bookclub.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookclub.models.DatabaseBook
import com.example.bookclub.models.mapToClubDatabaseBook
import com.example.bookclub.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectClubBookViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _state = MutableStateFlow(Result.success(emptyList<DatabaseBook>()))

    val state: StateFlow<Result<List<DatabaseBook>>> = _state

    init {
        viewModelScope.launch {
            try {
                val response = authRepository.getBooksForClubCreation()
                val books = response.data?.userBooks
                if(books == null || response.hasErrors()){
                    return@launch
                } else {
                    _state.value = Result.success(books.mapToClubDatabaseBook())
                }
            } catch(exception: Exception){
                _state.value = Result.failure(exception)
            }
        }
    }
}