package com.example.nikestore.components.imageview

import com.example.nikestore.customview.imageview.NikeImageView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView

interface ImageLoading {

    fun load(imageView:NikeImageView , uri:String)


}