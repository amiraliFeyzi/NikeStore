package com.example.nikestore.utils.exceptionhandler

import com.example.nikestore.R
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber

class NikeExceptionMapper {

    companion object{

        fun map(throwable: Throwable):NikeException{
            if (throwable is HttpException){
                try {
                    val errorJsonObject = JSONObject(throwable.response()?.errorBody()!!.string())
                    val errorMessage = errorJsonObject.getString("message")
                    return NikeException(if (throwable.code() == 401) NikeException.ErrorType.AUTH else NikeException.ErrorType.SIMPLE , serverMessage = errorMessage)
                }catch (exception:Exception){
                    Timber.e(exception)
                }
            }

            return NikeException(NikeException.ErrorType.SIMPLE , R.string.unknown_error)

        }

    }
}