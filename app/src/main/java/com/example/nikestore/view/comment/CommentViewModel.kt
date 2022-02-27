package com.example.nikestore.view.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.nikestore.base.NikeViewModel
import com.example.nikestore.model.dataclass.Comment
import com.example.nikestore.model.dataclass.Product
import com.example.nikestore.model.repository.comment.CommentRepository
import com.example.nikestore.utils.variables.EXTRA_KEY_DATA_ID

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CommentViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
, private val commentRepository: CommentRepository):NikeViewModel() {

    private val _productIdLiveData = MutableLiveData<Int>()
    private val _commentsLiveData = MutableLiveData<List<Comment>>()

    val commentsLiveData:LiveData<List<Comment>> get() = _commentsLiveData

    private fun getProductId():Int{
        val response = savedStateHandle.get<Product>(EXTRA_KEY_DATA_ID)
        _productIdLiveData.postValue(response!!.id)
        return response.id
    }

    fun getComments(){
        viewModelScope.launch(Dispatchers.IO) {
            commentRepository.getComments(getProductId().toString()).collect {
                _commentsLiveData.postValue(it)
            }
        }
    }


    fun addComment(title:String , content:String){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            commentRepository.addComment(getProductId().toString() , title , content)

        }
    }

}