package com.example.nikestore.base

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nikestore.utils.exceptionhandler.NikeExceptionMapper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

abstract class NikeViewModel:ViewModel() {

    val progressBarLiveData = MutableLiveData<Boolean>()


    val coroutineExceptionHandler = CoroutineExceptionHandler{coroutineContext, throwable ->
        EventBus.getDefault().post(NikeExceptionMapper.map(throwable))
        Timber.e(throwable)
    }


}