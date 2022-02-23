package com.example.nikestore.view.profile

import com.example.nikestore.base.NikeViewModel
import com.example.nikestore.model.`object`.TokenContainer
import com.example.nikestore.model.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository) :NikeViewModel() {
    val username: String
        get() = userRepository.getUserName()

    val isSignedIn: Boolean
        get() = TokenContainer.token != null

    fun signOut() = userRepository.signOut()
}