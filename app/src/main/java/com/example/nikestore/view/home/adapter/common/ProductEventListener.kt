package com.example.nikestore.view.home.adapter.common

import com.example.nikestore.model.dataclass.Product

interface ProductEventListener {
    fun onProductClick(product: Product)
    fun onFavoriteBtnClick(product: Product)
}