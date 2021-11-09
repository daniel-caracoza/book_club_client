package com.example.bookclub.viewModels

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.bookclub.models.SearchResultItem
import com.example.bookclub.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val authRepository: AuthRepository
):ViewModel() {

    fun searchResults(query: String, route: String): Flow<PagingData<SearchResultItem>> =
       authRepository.apiServiceSearch(query, route)
}