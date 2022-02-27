package com.example.nikestore.model.datasource.comment

import com.example.nikestore.model.apiService.ApiService
import com.example.nikestore.model.dataclass.Comment
import com.google.gson.JsonObject
import javax.inject.Inject

class CommentRemoteDataSource @Inject constructor(val apiService: ApiService):CommentDataSource {
    override suspend fun getComments(productId:String): List<Comment>  = apiService.getComments(productId.toInt())


    override suspend fun addComment(productId: String, title: String, content: String)  = apiService.addComment(
        JsonObject().apply {
            addProperty("title" , title)
            addProperty("content" , content)
            addProperty("product_id" , productId)
        })
}