package com.example.bookclub.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.security.crypto.EncryptedSharedPreferences
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
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    private val _recentSearchItems: MutableLiveData<UserWithSearchItems> by lazy {
        MutableLiveData<UserWithSearchItems>()
    }
    val recentSearchItems: LiveData<UserWithSearchItems>
            get() = _recentSearchItems

    init {
        getUserRecentSearches()
    }

    fun addUserRecentSearch(recentSearch: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val userId = sharedPreferences.getString("userId", null);
            userId?.let {
                val searchItem = SearchItem(
                    0,
                    it,
                    recentSearch
                )
                authRepository.addUserRecentSearch(searchItem)
                getUserRecentSearches()
            }

        }
    }

    private fun getUserRecentSearches() {
        viewModelScope.launch {
            val userId = sharedPreferences.getString("userId", null);
            userId?.let {
                _recentSearchItems.value = authRepository.getUserRecentSearchItems(userId)
            }
        }
    }

    fun deleteSearchItem(searchItem: SearchItem) {
        CoroutineScope(Dispatchers.IO).launch {
            authRepository.deleteSearchItem(searchItem)
            getUserRecentSearches()
        }
    }

}