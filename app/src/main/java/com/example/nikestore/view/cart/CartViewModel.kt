package com.example.nikestore.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nikestore.R
import com.example.nikestore.base.NikeViewModel
import com.example.nikestore.model.`object`.TokenContainer
import com.example.nikestore.model.dataclass.CartItem
import com.example.nikestore.model.dataclass.EmptyState
import com.example.nikestore.model.dataclass.PurchaseDetail
import com.example.nikestore.model.repository.cart.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import hilt_aggregated_deps._com_example_nikestore_di_RepositoryModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CartViewModel @Inject constructor(private val cartRepository: CartRepository) :NikeViewModel() {

    private val _cartItemsLiveData = MutableLiveData<List<CartItem>>()
    private val _purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()
    private val _emptyStateCart = MutableLiveData<EmptyState>()

    val cartItemsLiveData:LiveData<List<CartItem>> get() =  _cartItemsLiveData
    val purchaseDetailLiveData : LiveData<PurchaseDetail> get() =  _purchaseDetailLiveData
    val emptyStateCart:LiveData<EmptyState> get() = _emptyStateCart



    private fun getCartItems(){
        if (!TokenContainer.token.isNullOrEmpty()){
            viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                _emptyStateCart.postValue(EmptyState(false))
                cartRepository.get().collect {
                    if (it.cart_items.isNotEmpty()){
                        _cartItemsLiveData.postValue(it.cart_items)
                        _purchaseDetailLiveData.postValue(PurchaseDetail(it.total_price ,it.shipping_cost , it.payable_price))
                    }else{
                        _emptyStateCart.postValue(EmptyState(true , R.string.cartEmptyState))
                    }
                }
            }
        }else{
            _emptyStateCart.value = EmptyState(true , R.string.cartEmptyStateLoginRequired ,true )
        }
    }

    fun removeItemFromCart(cartItem: CartItem){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            try{
                cartRepository.remove(cartItem.cart_item_id)

            }finally {
                calculateAndPublishPurchaseDetail()
                _cartItemsLiveData.value?.let {
                    if (it.isEmpty()){
                        _emptyStateCart.postValue(EmptyState(true , R.string.cartEmptyState))
                    }
                }
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