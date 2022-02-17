package com.example.nikestore.model.repository.comment

import com.example.nikestore.model.dataclass.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {

    suspend fun getComments(productId:String):Flow<List<Comment>>
}