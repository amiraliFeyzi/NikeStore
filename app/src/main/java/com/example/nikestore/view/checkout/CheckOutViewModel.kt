package com.example.nikestore.view.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.nikestore.base.NikeViewModel
import com.example.nikestore.model.dataclass.Checkout
import com.example.nikestore.model.dataclass.Product
import com.example.nikestore.model.repository.cart.CartRepository
import com.example.nikestore.model.repository.order.OrderRepository
import com.example.nikestore.utils.variables.EXTRA_KEY_DATA
import com.example.nikestore.utils.variables.EXTRA_KEY_DATA_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CheckOutViewModel @Inject constructor (private val orderRepository: OrderRepository , private val cartRepository: CartRepository):NikeViewModel()  {

    private val _checkOutLiveData = MutableLiveData<Checkout>()

    val checkOutLiveData:LiveData<Checkout> get() = _checkOutLiveData


    fun checkOut(orderId:Int){
        viewModelScope.launch (Dispatchers.IO){
            orderRepository.checkout(orderId).collect {
                _checkOutLiveData.postValue(it)

            }
        }
    }
}