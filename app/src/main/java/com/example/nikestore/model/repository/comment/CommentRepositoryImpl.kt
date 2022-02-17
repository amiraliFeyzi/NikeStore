package com.example.nikestore.model.repository.comment

import com.example.nikestore.model.dataclass.Comment
import com.example.nikestore.model.datasource.comment.CommentDataSource
import com.example.nikestore.model.datasource.comment.CommentRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(val commentRemoteDataSource: CommentDataSource):CommentRepository {
    override suspend fun getComments(productId: String): Flow<List<Comment>> {
       return flow {
           emit(commentRemoteDataSource.getComments(productId))
       }
    }
}