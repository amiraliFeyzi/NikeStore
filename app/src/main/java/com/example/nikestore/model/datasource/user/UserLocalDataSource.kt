package com.example.nikestore.model.datasource.user

import android.content.SharedPreferences
import com.example.nikestore.model.`object`.TokenContainer
import com.example.nikestore.model.dataclass.MessageResponse
import com.example.nikestore.model.dataclass.TokenResponse

class UserLocalDataSource(private val sharedPreferences: SharedPreferences) :UserDataSource {
    override suspend fun login(username: String, password: String): TokenResponse {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(username: String, password: String): MessageResponse {
        TODO("Not yet implemented")
    }

    override  fun loadToken() {
        TokenContainer.update(
            sharedPreferences.getString("access_token", null),
            sharedPreferences.getString("refresh_token", null)
        )
    }

    override  fun saveToken(token: String, refreshToken: String) {
        sharedPreferences.edit().apply {
            putString("access_token", token)
            putString("refresh_token", refreshToken)
        }.apply()
    }

    override  fun saveUsername(username: String) {
        sharedPreferences.edit().apply {
            putString("username", username)
        }.apply()
    }

    override  fun getUsername(): String  =sharedPreferences.getString("username", "") ?: ""

    override  fun signOut() {
       sharedPreferences.edit().apply {
            clear()
        }.apply()
    }

}