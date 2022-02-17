package com.example.nikestore.base

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.example.nikestore.components.imageview.ImageLoading
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

abstract class NikeFragment:Fragment() , NikeView {


    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout


    override val viewContext: Context?
        get() = context

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}