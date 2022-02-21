package com.example.nikestore.view.main

import androidx.lifecycle.viewModelScope
import com.example.nikestore.base.NikeViewModel
import com.example.nikestore.model.`object`.TokenContainer
import com.example.nikestore.model.repository.cart.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(private val cartRepository: CartRepository) :NikeViewModel() {

    fun getCartItemsCount(){
        if (!TokenContainer.token.isNullOrEmpty()){
            viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                cartRepository.getCartItemsCount().collect {
                    EventBus.getDefault().postSticky(it)
                }
            }
        }

    }

}