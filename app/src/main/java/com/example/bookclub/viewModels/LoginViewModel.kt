package com.example.bookclub.viewModels

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.exception.ApolloNetworkException
import com.example.bookclub.models.User
import com.example.bookclub.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    private val _isLoginButtonEnabled = MutableLiveData<Boolean>()
    val isLoginButtonEnabled: LiveData<Boolean>
        get() = _isLoginButtonEnabled

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean>
        get() = _loginSuccess

    private val _loginFailed = MutableLiveData<Boolean>()
    val loginFailed: LiveData<Boolean>
        get() = _loginFailed

    init {
        _isLoginButtonEnabled.value = false
        _loginSuccess.value = false
        _loginFailed.value = false
    }

    var username: String = ""
    var password: String = ""

    fun login(){
        viewModelScope.launch {
            try {
                val response  = repository.loginUser(username, password)
                if(response.data == null){
                    _loginFailed.value = true
                } else {
                    val accessToken = response.data!!.login!!.accessToken
                    val networkUser = response.data!!.login!!.user
                    val domainUser = User(networkUser.id, networkUser.username)
                    repository.insertUser(domainUser)
                    with(sharedPreferences.edit()){
                        putString("token", accessToken)
                        putString("userId", domainUser.userId)
                        apply()
                    }
                    _loginSuccess.value = true
                }
            } catch(error: ApolloNetworkException){
                Log.e("Login", error.toString())
            }
        }
    }

    fun isFormComplete(){
        _isLoginButtonEnabled.value = username.isNotEmpty() && password.isNotEmpty()
    }

}