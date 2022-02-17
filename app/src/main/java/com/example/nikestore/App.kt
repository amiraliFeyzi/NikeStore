package com.example.nikestore

import android.app.Application
import com.example.nikestore.model.repository.user.UserRepository
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App:Application() {

    @Inject
    lateinit var userRepository: UserRepository
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        Fresco.initialize(this)


        userRepository.loadToken()
    }
}