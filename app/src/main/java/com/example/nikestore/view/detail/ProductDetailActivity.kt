package com.example.nikestore.view.detail

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.base.NikeActivity
import com.example.nikestore.components.imageview.ImageLoading
import com.example.nikestore.customview.scroll.ObservableScrollViewCallbacks
import com.example.nikestore.customview.scroll.ScrollState
import com.example.nikestore.databinding.ActivityProductDetailBinding
import com.example.nikestore.model.dataclass.Comment
import com.example.nikestore.model.dataclass.Product
import com.example.nikestore.utils.formatPrice
import com.example.nikestore.utils.variables.EXTRA_KEY_DATA_ID
import com.example.nikestore.view.comment.CommentActivity
import com.example.nikestore.view.comment.CommentAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_product_detail.*
import javax.inject.Inject
@AndroidEntryPoint
class ProductDetailActivity : NikeActivity() {
    private lateinit var binding:ActivityProductDetailBinding

    private val viewModel:ProductDetailViewModel by viewModels()

    @Inject
    lateinit var imageLoading:ImageLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getProduct()
        viewModel.getComments()
        observeData()



    }


    private fun observeData(){
        viewModel.productLiveData.observe(this){
            setUpUi(it)
        }


        viewModel.commentsLiveData.observe(this){
            setCommentsList(it)
        }


    }

    private fun setUpUi(product:Product){
        imageLoading.load(productIv, product.image)
        binding.titleTv.text = product.title
        binding.previousPriceTv.text = formatPrice(product.previous_price)
        binding.previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        binding.currentPriceTv.text = formatPrice(product.price)
        binding.toolbarTitleTv.text = product.title

        setAnimations()


        binding.addToCartBtn.setOnClickListener {
            viewModel.onAddToCartBtn()
            showSnackBar(getString(R.string.success_addToCart))
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

    }

    private fun setCommentsList(comments:List<Comment>){
        val commentAdapter = CommentAdapter()
        commentAdapter.setData(comments)
        binding.commentsRv.adapter  = commentAdapter
        binding.commentsRv.layoutManager = LinearLayoutManager(this , RecyclerView.VERTICAL ,false)
        binding.commentsRv.isNestedScrollingEnabled = false

        if (comments.size>3){
            binding.viewAllCommentsBtn.visibility = View.VISIBLE
            binding.viewAllCommentsBtn.setOnClickListener {
                startActivity(Intent(this , CommentActivity::class.java).apply {
                    putExtra(EXTRA_KEY_DATA_ID , viewModel.productLiveData.value)
                })
            }
        }
    }

    private fun setAnimations(){
        binding.productIv.post {
            val productHeight = binding.productIv.height
            val toolbar = binding.toolbarView
            val productImageView =binding.productIv
            binding.observableScrollView.addScrollViewCallbacks(object :ObservableScrollViewCallbacks{
                override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
                    toolbar.alpha = scrollY.toFloat() / productHeight.toFloat()
                    productImageView.translationY = scrollY.toFloat() / 2
                }

                override fun onDownMotionEvent() {

                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {

                }

            })
        }

    }
}