package com.example.nikestore.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nikestore.base.NikeViewModel
import com.example.nikestore.model.dataclass.Banner
import com.example.nikestore.model.dataclass.Product
import com.example.nikestore.model.repository.product.ProductRepository
import com.example.nikestore.utils.variables.SORT_LATEST
import com.example.nikestore.utils.variables.SORT_POPULAR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productRepository: ProductRepository) :NikeViewModel() {

    private val _latestProductList = MutableLiveData<List<Product>>()
    private val _bannersList = MutableLiveData<List<Banner>>()
    private val _popularProductList = MutableLiveData<List<Product>>()

    val latestProductList:LiveData<List<Product>> get() =  _latestProductList
    val bannersList:LiveData<List<Banner>> get() = _bannersList
    val popularProductList:LiveData<List<Product>> get() = _popularProductList

    init {
        getLatestProductList()
        getBanners()
        getPopularProductList()
    }
    private  fun getLatestProductList(){
        progressBarLiveData.value =true

        viewModelScope.launch (Dispatchers.IO){
            productRepository.getProducts(SORT_LATEST)
                .onCompletion { progressBarLiveData.postValue(false) }
                .collect {
                    _latestProductList.postValue(it)
            }

        }
    }

    private fun getPopularProductList(){
        progressBarLiveData.value =true

        viewModelScope.launch (Dispatchers.IO){
            productRepository.getProducts(SORT_POPULAR)
                .onCompletion { progressBarLiveData.postValue(false) }
                .collect {
                    _popularProductList.postValue(it)

                }

        }
    }
    private fun getBanners(){
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getBanners().collect {
                _bannersList.postValue(it)
            }
        }

    }

}