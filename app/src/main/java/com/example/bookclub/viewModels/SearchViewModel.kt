package com.example.bookclub.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookclub.models.SearchItem
import com.example.bookclub.models.UserWithSearchItems
import com.example.bookclub.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    private val _recentSearchItems: MutableLiveData<UserWithSearchItems> by lazy {
        MutableLiveData<UserWithSearchItems>()
    }
    val recentSearchItems: LiveData<UserWithSearchItems>
            get() = _recentSearchItems

    init {
        getUserRecentSearches()
    }

    private fun getUserRecentSearches() {
        viewModelScope.launch {
            _recentSearchItems.value = authRepository.getUserRecentSearchItems()
        }
    }

    fun deleteSearchItem(searchItem: SearchItem) {
        viewModelScope.launch {
            authRepository.deleteSearchItem(searchItem)
        }
    }

}