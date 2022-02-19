package com.example.nikestore.view.auth

import androidx.lifecycle.LiveData
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

    private val _finishWorkLiveData = MutableLiveData<Boolean>()
    val finishWorkLiveData:LiveData<Boolean> get() = _finishWorkLiveData
    fun login(username: String, password: String){
        viewModelScope.launch(Dispatchers.IO  +coroutineExceptionHandler) {
            _finishWorkLiveData.postValue(false)
            try {
                val tokenResponse = userRepository.login(username , password)
                userRepository.onSuccessfulLogin(username , tokenResponse)
            }finally {
                _finishWorkLiveData.postValue(true)
            }

        }
    }

    fun signUp(username: String, password: String){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            try {
                val message =  userRepository.signUp(username , password)
            }finally {
                login(username , password)
            }
        }

    }

}