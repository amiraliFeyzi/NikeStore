package com.example.nikestore.view.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nikestore.base.NikeViewModel
import com.example.nikestore.model.dataclass.OrderHistoryItem
import com.example.nikestore.model.repository.order.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class OrderHistoryViewModel @Inject constructor(private val orderRepository: OrderRepository) :NikeViewModel() {
    private val _ordersLiveData = MutableLiveData<List<OrderHistoryItem>>()

    val ordersLiveData:LiveData<List<OrderHistoryItem>> get() = _ordersLiveData

    init {
        getListOrders()
    }

    private fun getListOrders(){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            orderRepository.listOrders().collect {
                _ordersLiveData.postValue(it)
            }
        }
    }
}