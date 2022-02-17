package com.example.nikestore.view.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nikestore.R
import com.example.nikestore.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    lateinit var binding:ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_auth, LogInFragment())
        }.commit()

    }


}