package com.example.bookclub.viewModels

import androidx.lifecycle.ViewModel
import com.example.bookclub.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): ViewModel() {
}