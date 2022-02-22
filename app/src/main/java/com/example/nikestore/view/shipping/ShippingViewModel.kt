package com.example.nikestore.view.shipping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nikestore.base.NikeViewModel
import com.example.nikestore.model.dataclass.SubmitOrderResult
import com.example.nikestore.model.repository.order.OrderRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ShippingViewModel @Inject constructor(private val orderRepository: OrderRepository) :NikeViewModel() {

    private val _submitLiveData= MutableLiveData<SubmitOrderResult>()
    val submitLiveData:LiveData<SubmitOrderResult> get() = _submitLiveData
    fun submit( firstName: String, lastName: String, postalCode: String, phoneNumber: String, address: String,
                paymentMethod: String){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            orderRepository.submit(firstName, lastName, postalCode, phoneNumber, address, paymentMethod).collect {
                _submitLiveData.postValue(it)
            }
        }

    }
}