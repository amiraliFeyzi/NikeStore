package com.example.nikestore.view.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nikestore.base.NikeViewModel
import com.example.nikestore.model.dataclass.TokenResponse
import com.example.nikestore.model.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository):NikeViewModel() {

    fun login(username: String, password: String){
        viewModelScope.launch(Dispatchers.IO ) {
            val tokenResponse = userRepository.login(username , password)
            userRepository.onSuccessfulLogin(username , tokenResponse)
        }
    }

    fun signUp(username: String, password: String){
        viewModelScope.launch(Dispatchers.IO ) {
            userRepository.signUp(username , password)
            userRepository.login(username , password)
        }
    }

}