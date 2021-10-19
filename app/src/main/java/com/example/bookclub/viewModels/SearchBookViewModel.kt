package com.example.bookclub.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookclub.models.SearchBook
import com.example.bookclub.models.mapToBookInput
import com.example.bookclub.models.mapToSearchBook
import com.example.bookclub.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchBookViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val TAG = "SearchBookViewModel"

    private val _addToListIsEnabled = MutableLiveData<Boolean>()

    val addToListIsEnabled: LiveData<Boolean>
        get() = _addToListIsEnabled


    fun disableAddToListButton(){
        _addToListIsEnabled.value = false
    }

    private fun enableAddToListButton(){
        _addToListIsEnabled.value = true
    }

    fun isBookInReadingList(isbn: String) {
        viewModelScope.launch {
            val response = authRepository.doesUserBookExist(isbn)
            val isBookInReadingList = response.data?.doesUserBookExist
            if(isBookInReadingList == null || response.hasErrors()){
                return@launch
            }
            if(!isBookInReadingList){
                enableAddToListButton()
            } else {
                disableAddToListButton()
            }
        }
    }

    fun findByISBN(isbn: String): LiveData<SearchBook> {
        val searchBookLiveData = MutableLiveData<SearchBook>()
        viewModelScope.launch {
            val response = authRepository.findByISBN(isbn)
            val searchBook = response.data!!.findByISBN!!.mapToSearchBook()
            searchBookLiveData.postValue(searchBook)
        }
        return searchBookLiveData
    }

    fun addToReadingList(book: SearchBook): LiveData<Boolean> {
        val isSuccess = MutableLiveData<Boolean>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = authRepository.addToReadingList(book.mapToBookInput())
            if(response.data?.addToReadingList == null || response.hasErrors()){
                enableAddToListButton()
                isSuccess.postValue(false)
            }
            isSuccess.postValue(true)
        }
        return isSuccess
    }




}