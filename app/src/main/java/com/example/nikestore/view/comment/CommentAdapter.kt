package com.example.nikestore.view.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.databinding.ItemCommentBinding
import com.example.nikestore.model.dataclass.Comment
import java.util.ArrayList

class CommentAdapter :RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    val commentsList = ArrayList<Comment>()

    fun setData(comments:List<Comment>){
        this.commentsList.clear()
        this.commentsList.addAll(comments)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemCommentBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(comment: Comment){
            binding.commentTitleTv.text = comment.title
            binding.commentDateTv.text = comment.date
            binding.commentAuthor.text = comment.author.email
            binding.commentContentTv.text = comment.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(commentsList[position])
    }

    override fun getItemCount(): Int =  if (commentsList.size > 3) 3 else commentsList.size
}