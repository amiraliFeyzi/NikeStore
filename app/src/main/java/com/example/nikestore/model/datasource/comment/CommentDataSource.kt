package com.example.nikestore.model.datasource.comment

import com.example.nikestore.model.dataclass.Comment

interface CommentDataSource {

    suspend fun getComments(productId:String):List<Comment>

    suspend fun addComment(productId: String , title:String , content:String)
}