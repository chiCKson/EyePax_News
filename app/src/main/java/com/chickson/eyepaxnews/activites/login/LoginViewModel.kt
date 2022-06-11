package com.chickson.eyepaxnews.activites.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chickson.eyepaxnews.models.User
import com.chickson.eyepaxnews.network.NewsResult
import com.chickson.eyepaxnews.repositories.UserRepository
import com.chickson.eyepaxnews.util.hash
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject
constructor(
    private val userRepository: UserRepository
): ViewModel( ) {

    var registerPressed = mutableStateOf(false)
    var loginPressed = mutableStateOf(false)
    var loginSuccess = MutableLiveData<Boolean>()
    var username = mutableStateOf("")
    var password = mutableStateOf("")
    var error = MutableLiveData<String>()

    fun registerUser() {
        viewModelScope.launch {
            userRepository.saveUsers(users = listOf(User(username = username.value,password = password.value.hash())))
                .onStart {
                    //isLoading.value = true
                }
                .catch { e ->
                    //isLoading.value = false
                }
                .collect { response ->
                    if (response){
                        registerPressed.value = false
                        loginPressed.value = false
                    } else {
                        error.postValue("User Already Exist!")
                    }
                }
        }
    }

    fun login() = viewModelScope.launch {
        userRepository.findUserByUserName(username = username.value)
            .onStart {
            //isLoading.value = true
            }
            .catch { e ->
                //isLoading.value = false
            }
            .collect { response ->
                if (password.value.hash() == response.password){
                    loginSuccess.value = true
                }
            }
    }
}