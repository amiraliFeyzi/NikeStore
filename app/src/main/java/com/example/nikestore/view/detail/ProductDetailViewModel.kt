package com.example.nikestore.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.nikestore.base.NikeViewModel
import com.example.nikestore.model.dataclass.Comment
import com.example.nikestore.model.dataclass.Product
import com.example.nikestore.model.repository.cart.CartRepository
import com.example.nikestore.model.repository.comment.CommentRepository
import com.example.nikestore.model.repository.product.ProductRepository
import com.example.nikestore.utils.variables.EXTRA_KEY_DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle, private val commentRepository: CommentRepository ,
private val cartRepository: CartRepository , private val productRepository: ProductRepository):NikeViewModel() {

    private val _productLiveData = MutableLiveData<Product>()
    private val _commentsLiveData = MutableLiveData<List<Comment>>()

    val productLiveData:LiveData<Product> get() = _productLiveData
    val commentsLiveData:LiveData<List<Comment>> get() = _commentsLiveData


    fun getProduct(){
        val response = savedStateHandle.get<Product>(EXTRA_KEY_DATA)
        _productLiveData.value = response!!
    }

    fun getComments(){
        viewModelScope.launch(Dispatchers.IO) {
            commentRepository.getComments(_productLiveData.value!!.id.toString()).collect {
                _commentsLiveData.postValue(it)
            }
        }
    }


    fun onAddToCartBtn() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            cartRepository.addToCart(_productLiveData.value!!.id)
        }
    }

    fun addProductToFavorite(product: Product){
        if(product.isFavorite){
            viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                try{
                    productRepository.deleteFromFavorites(product)

                }finally {
                    product.isFavorite= false
                }
            }
        }else{
            viewModelScope.launch(Dispatchers.IO+ coroutineExceptionHandler) {
                try {
                    productRepository.addToFavorites(product)

                }finally {
                    product.isFavorite = true
                }
            }
        }

    }

}
