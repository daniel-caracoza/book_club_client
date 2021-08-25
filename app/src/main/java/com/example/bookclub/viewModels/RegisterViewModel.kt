package com.example.bookclub.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.exception.ApolloNetworkException
import com.example.bookclub.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel()
class RegisterViewModel @Inject constructor(
    private val repository: Repository): ViewModel() {


    private val _isFormComplete = MutableLiveData<Boolean>()
    val isFormComplete: LiveData<Boolean>
        get() = _isFormComplete

    private val _registerSuccess = MutableLiveData<Boolean>()
    val registerSuccess: LiveData<Boolean>
        get() = _registerSuccess

    var username: String = ""
    var email: String = ""
    var password: String = ""

    init {
        _isFormComplete.value = false
        _registerSuccess.value = false
    }

    //tie to register button
    fun registerUser(){
        viewModelScope.launch {
            try {
                val response = repository.registerUser(username, email, password)
                if(response.data != null){
                    _registerSuccess.value = true
                }
            } catch (error: ApolloNetworkException){
                Log.e("Register", error.toString())
            }
        }
    }

    fun isFormComplete() {
        _isFormComplete.value = username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
    }
}