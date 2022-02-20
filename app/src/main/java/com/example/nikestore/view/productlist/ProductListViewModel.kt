package com.example.nikestore.view.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.nikestore.R
import com.example.nikestore.base.NikeViewModel
import com.example.nikestore.model.dataclass.Product
import com.example.nikestore.model.repository.product.ProductRepository
import com.example.nikestore.utils.variables.EXTRA_KEY_PRODUCT_SORT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProductListViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle , private val productRepository: ProductRepository) :NikeViewModel() {

    private val _productLiveData =MutableLiveData<List<Product>>()
    private val _selectedSortLiveData = MutableLiveData<Int>()

    val productLiveData:LiveData<List<Product>> get() = _productLiveData
    val selectedSortLiveData:LiveData<Int> get() =  _selectedSortLiveData

    val sortTitles = arrayOf(R.string.sortLatest , R.string.sortPopular , R.string.sortPriceHighToLow , R.string.sortPriceLowToHigh)


    init {
        getProductList(getSortFromSavedStateHandle())
        _selectedSortLiveData.value = sortTitles[getSortFromSavedStateHandle().toInt()]
    }
    fun getSortFromSavedStateHandle(): String {
        return savedStateHandle.get<String>(EXTRA_KEY_PRODUCT_SORT)!!
    }

    fun onSelectedSortChangeByUser(sort:Int){
        val sortNow = getSortFromSavedStateHandle()
        sort == sortNow.toInt()
        this._selectedSortLiveData.value = sortTitles[sort]
        getProductList(sort.toString())
    }

    fun getProductList(sort:String){
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getProducts(sort.toString()).collect {
                _productLiveData.postValue(it)
            }
        }
    }
}