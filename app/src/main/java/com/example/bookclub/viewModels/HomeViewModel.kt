package com.example.bookclub.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookclub.models.DatabaseBook
import com.example.bookclub.models.mapToDatabaseBook
import com.example.bookclub.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    fun getUserBooks(): LiveData<List<DatabaseBook>> {
        val liveResponse = MutableLiveData<List<DatabaseBook>>()
        viewModelScope.launch {
            val response = authRepository.getUserBooks()
            val booksList = response.data!!.userBooks
            val domainBookList = booksList?.mapToDatabaseBook()
            liveResponse.postValue(domainBookList)
        }
        return liveResponse
    }
}