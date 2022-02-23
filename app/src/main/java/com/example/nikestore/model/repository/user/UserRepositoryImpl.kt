package com.example.nikestore.model.repository.user

import com.example.nikestore.model.`object`.TokenContainer
import com.example.nikestore.model.dataclass.MessageResponse
import com.example.nikestore.model.dataclass.TokenResponse
import com.example.nikestore.model.datasource.user.UserDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber

class UserRepositoryImpl(private val userRemoteDataSource:UserDataSource ,private val userLocalDataSource: UserDataSource) :UserRepository {

    override suspend fun onSuccessfulLogin(username: String, tokenResponse: TokenResponse) {
        TokenContainer.update(tokenResponse.access_token, tokenResponse.refresh_token)
        userLocalDataSource.saveToken(tokenResponse.access_token, tokenResponse.refresh_token)
        userLocalDataSource.saveUsername(username)
    }

    override suspend fun login(username: String, password: String): TokenResponse {
        return userRemoteDataSource.login(username , password)

    }

    override suspend fun signUp(username: String, password: String):MessageResponse{
        return userLocalDataSource.signUp(username , password)
    }

    override  fun loadToken() {
        userLocalDataSource.loadToken()
    }

    override  fun getUserName(): String {
        return userLocalDataSource.getUsername()
    }

    override  fun signOut() {
        userLocalDataSource.signOut()
        TokenContainer.update(null , null)
    }




}