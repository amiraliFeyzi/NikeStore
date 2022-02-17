package com.example.nikestore.utils.exceptionhandler

import androidx.annotation.StringRes

class NikeException(val errorType: ErrorType , @StringRes val userErrorMessage:Int = 0 , val serverMessage:String? = null ):Throwable() {

    enum class ErrorType{
        SIMPLE,DIALOG,AUTH
    }
}