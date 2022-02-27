package com.example.nikestore.view.comment.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.nikestore.R
import com.example.nikestore.databinding.ActivityAddCommentBinding
import com.example.nikestore.view.comment.CommentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddCommentActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddCommentBinding

    private val viewModel:CommentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.enterCommentBtn.setOnClickListener {
            viewModel.addComment(binding.titleCommentEt.text.toString() , binding.ContentCommentEt.text.toString())
            finish()
        }


    }
}