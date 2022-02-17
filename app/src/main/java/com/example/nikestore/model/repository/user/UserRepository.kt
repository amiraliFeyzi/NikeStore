package com.example.nikestore.model.repository.user

import com.example.nikestore.model.dataclass.MessageResponse
import com.example.nikestore.model.dataclass.TokenResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun login(username: String, password: String):TokenResponse
    suspend fun signUp(username: String, password: String): MessageResponse
    fun loadToken()
    fun getUserName():String
    fun signOut()
    suspend fun onSuccessfulLogin(username: String, tokenResponse: TokenResponse)
}