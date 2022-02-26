package com.example.nikestore.view.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nikestore.base.NikeViewModel
import com.example.nikestore.model.dataclass.Product
import com.example.nikestore.model.repository.product.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteProductViewModel @Inject constructor(private val productRepository: ProductRepository):NikeViewModel() {

    private val _productsLiveData = MutableLiveData<List<Product>>()


    val productsLiveData:LiveData<List<Product>> get() =  _productsLiveData

    fun removeProductFromFavorite(product:Product){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            productRepository.deleteFromFavorites(product)
        }
    }

    init {
        getProductList()
    }

    private fun getProductList(){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            productRepository.getFavoriteProducts().collect {
                _productsLiveData.postValue(it)
            }
        }
    }
}