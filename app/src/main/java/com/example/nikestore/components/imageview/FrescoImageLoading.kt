package com.example.nikestore.components.imageview

import com.example.nikestore.customview.imageview.NikeImageView
import com.facebook.drawee.view.SimpleDraweeView

class FrescoImageLoading:ImageLoading {

    override fun load(imageView: NikeImageView, uri: String) {
        if (imageView is SimpleDraweeView){
            imageView.setImageURI(uri)
        }else{
            throw IllegalAccessError("ImageView must be instance of SimpleDraweeView")
        }

    }
}