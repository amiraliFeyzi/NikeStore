package com.example.nikestore.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nikestore.base.NikeViewModel
import com.example.nikestore.model.`object`.TokenContainer
import com.example.nikestore.model.dataclass.CartItem
import com.example.nikestore.model.dataclass.PurchaseDetail
import com.example.nikestore.model.repository.cart.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CartViewModel @Inject constructor(private val cartRepository: CartRepository) :NikeViewModel() {

    private val _cartItemsLiveData = MutableLiveData<List<CartItem>>()
    private val _purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()

    val cartItemsLiveData:LiveData<List<CartItem>> get() =  _cartItemsLiveData
    val purchaseDetailLiveData : LiveData<PurchaseDetail> get() =  _purchaseDetailLiveData


    private fun getCartItems(){
        if (!TokenContainer.token.isNullOrEmpty()){
            viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                cartRepository.get().collect {
                    if (it.cart_items.isNotEmpty()){
                        _cartItemsLiveData.postValue(it.cart_items)
                        _purchaseDetailLiveData.postValue(PurchaseDetail(it.total_price ,it.shipping_cost , it.payable_price))
                    }
                }
            }
        }
    }

    fun removeItemFromCart(cartItem: CartItem){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            try{
                cartRepository.remove(cartItem.cart_item_id)

            }finally {
                calculateAndPublishPurchaseDetail()
            }

        }
    }

    fun increaseCartItemCount(cartItem: CartItem){
       viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
           try{
               cartRepository.changeCount(cartItem.cart_item_id ,++cartItem.count)

           }finally {
               calculateAndPublishPurchaseDetail()
           }


       }


    }

    fun decreaseCartItemCount(cartItem: CartItem){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            try{
                cartRepository.changeCount(cartItem.cart_item_id ,--cartItem.count)

            }finally {
                calculateAndPublishPurchaseDetail()
            }

        }


    }

    fun refresh(){
        getCartItems()
    }

    private fun calculateAndPublishPurchaseDetail() {
        _cartItemsLiveData.value?.let { cartItems ->
            _purchaseDetailLiveData.value?.let { purchaseDetail ->
                var totalPrice = 0
                var payablePrice = 0
                cartItems.forEach {
                    totalPrice += it.product.price * it.count
                    payablePrice += (it.product.price - it.product.discount) * it.count
                }

                purchaseDetail.totalPrice = totalPrice
                purchaseDetail.payable_price = payablePrice
                _purchaseDetailLiveData.postValue(purchaseDetail)
            }
        }
    }

}