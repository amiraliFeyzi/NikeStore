package com.example.nikestore.model.dataclass

import androidx.annotation.StringRes

data class EmptyState(
    val mustShow: Boolean,
    @StringRes val messageResId: Int = 0,
    val mustShowCallToActionButton: Boolean = false
)