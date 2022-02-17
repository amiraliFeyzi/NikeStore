package com.example.nikestore.model.datasource.user

import com.example.nikestore.model.apiService.ApiService
import com.example.nikestore.model.dataclass.MessageResponse
import com.example.nikestore.model.dataclass.TokenResponse
import com.example.nikestore.utils.CLIENT_ID
import com.example.nikestore.utils.CLIENT_SECRET
import com.google.gson.JsonObject

class UserRemoteDataSource(private val apiService: ApiService) :UserDataSource {
    override suspend fun login(username: String, password: String): TokenResponse {
        return apiService.login(JsonObject().apply {
            addProperty("username",username)
            addProperty("password",password)
            addProperty("grant_type","password")
            addProperty("client_id", CLIENT_ID)
            addProperty("client_secret", CLIENT_SECRET)
        })
    }

    override suspend fun signUp(username: String, password: String): MessageResponse {
        return apiService.signUp(JsonObject().apply {
            addProperty("email",username)
            addProperty("password",password)
        })
    }

    override  fun loadToken() {
        TODO("Not yet implemented")
    }

    override  fun saveToken(token: String, refreshToken: String) {
        TODO("Not yet implemented")
    }

    override  fun saveUsername(username: String) {
        TODO("Not yet implemented")
    }

    override fun getUsername(): String {
        TODO("Not yet implemented")
    }

    override  fun signOut() {
        TODO("Not yet implemented")
    }
}