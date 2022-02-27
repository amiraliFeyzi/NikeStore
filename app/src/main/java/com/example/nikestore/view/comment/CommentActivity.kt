package com.example.nikestore.view.comment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.base.NikeActivity
import com.example.nikestore.databinding.ActivityCommentBinding
import com.example.nikestore.model.dataclass.Comment
import com.example.nikestore.view.comment.add.AddCommentActivity
import com.example.nikestore.view.detail.ProductDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentActivity : NikeActivity() {
    private lateinit var binding:ActivityCommentBinding
    private val viewModel:CommentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getComments()
        observeData()



        binding.commentToolbar.onBackButtonClickListener = View.OnClickListener {
            finish()
        }
    }

    private fun observeData(){
        viewModel.commentsLiveData.observe(this){
            setUI(it)
        }
        viewModel.progressBarLiveData.observe(this){
            setProgressIndicator(it)
        }
    }
    private fun setUI(comments:List<Comment>){
        val commentAdapter = CommentAdapter()
        commentAdapter.setData(comments)
        binding.commentsRv.layoutManager = LinearLayoutManager(this , RecyclerView.VERTICAL ,false)
        binding.commentsRv.adapter = commentAdapter

        binding.addCommentFab.setOnClickListener {
            startActivity(Intent(this , AddCommentActivity::class.java))
        }

    }
}