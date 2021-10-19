package com.example.bookclub.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookclub.models.SearchResultItem
import com.example.bookclub.models.mapToSearchResultItem
import com.example.bookclub.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val authRepository: AuthRepository
):ViewModel() {

    fun searchResults(query: String, route: String): LiveData<List<SearchResultItem>> {
        val searchResults = MutableLiveData<List<SearchResultItem>>()
        viewModelScope.launch {
            val results = authRepository.apiServiceSearch(query, route)
            searchResults.postValue(results.data?.apiServiceSearch?.mapToSearchResultItem())
        }
        return searchResults
    }
}