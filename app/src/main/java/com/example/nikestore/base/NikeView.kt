package com.example.nikestore.base

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.nikestore.R
import com.example.nikestore.utils.exceptionhandler.NikeException
import com.example.nikestore.view.auth.AuthActivity
import com.example.nikestore.view.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

interface NikeView {
    val rootView: CoordinatorLayout?
    val viewContext:Context?
    fun setProgressIndicator(mustShow:Boolean){
        rootView?.let {
            viewContext?.let { context ->
                if(context is MainActivity){
                    Timber.i("Context must be not a MainActivity")

                }else{
                    var viewLoading = it.findViewById<View>(R.id.frame_view_loading)
                    if (viewLoading == null){
                        viewLoading = LayoutInflater.from(context).inflate(R.layout.view_loading , it)
                        it.addView(viewLoading)
                    }

                    viewLoading?.visibility = if (mustShow) View.VISIBLE else View.GONE
                }


            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showError(nikeException: NikeException) {
      viewContext?.let {
          if (it is MainActivity){
              Timber.i("Context must be not a MainActivity")
          }else{
              when (nikeException.errorType) {
                  NikeException.ErrorType.SIMPLE -> showSnackBar(
                      nikeException.serverMessage ?: it.getString(nikeException.userErrorMessage)
                  )

                  NikeException.ErrorType.AUTH -> {
                      it.startActivity(Intent(it, AuthActivity::class.java))
                      Toast.makeText(it, nikeException.serverMessage, Toast.LENGTH_SHORT).show()
                  }
              }
          }

      }

    }


    fun showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        rootView?.let {
            Snackbar.make(it, message, duration).show()
        }
    }
}