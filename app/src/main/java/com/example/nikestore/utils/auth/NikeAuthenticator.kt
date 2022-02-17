package com.example.nikestore.utils.auth

import com.example.nikestore.model.`object`.TokenContainer
import com.example.nikestore.model.apiService.ApiService
import com.example.nikestore.model.dataclass.TokenResponse
import com.example.nikestore.model.datasource.user.UserLocalDataSource
import com.example.nikestore.utils.CLIENT_ID
import com.example.nikestore.utils.CLIENT_SECRET
import com.google.gson.JsonObject
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class NikeAuthenticator:Authenticator {

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var userLocalDataSource: UserLocalDataSource
    override fun authenticate(route: Route?, response: Response): Request? {
       if(TokenContainer.token != null && TokenContainer.refreshToken != null && !response.request.url.pathSegments.last().equals("token",false)){
           try{
               val token = refreshToke()
               if (token.isEmpty()){
                   return null
               }
               return response.request.newBuilder().header("Authorization", "Bearer $token")
                   .build()
           }catch (e:Exception){
               Timber.e(e)
           }
       }
        return null
    }

    fun refreshToke():String{
        val response : retrofit2.Response<TokenResponse> = apiService.refreshToken(JsonObject().apply {
            addProperty("grant_type", "refresh_token")
            addProperty("refresh_token", TokenContainer.refreshToken)
            addProperty("client_id", CLIENT_ID)
            addProperty("client_secret", CLIENT_SECRET)
        }).execute()

        response.body()?.let {
            TokenContainer.update(it.access_token , it.refresh_token)
            userLocalDataSource.saveToken(it.access_token , it.refresh_token)
            return it.access_token
        }

        return ""
    }
}