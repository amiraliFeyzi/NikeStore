package com.example.nikestore.model.datasource.user

import com.example.nikestore.model.dataclass.MessageResponse
import com.example.nikestore.model.dataclass.TokenResponse

interface UserDataSource {


    suspend fun login(username: String, password: String): TokenResponse
    suspend fun signUp(username: String, password: String): MessageResponse
    fun loadToken()
    fun saveToken(token: String, refreshToken: String)
    fun saveUsername(username: String)
    fun getUsername(): String
    fun signOut()
}