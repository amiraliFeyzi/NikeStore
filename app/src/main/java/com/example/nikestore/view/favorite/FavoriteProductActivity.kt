package com.example.nikestore.view.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.base.NikeActivity
import com.example.nikestore.components.imageview.ImageLoading
import com.example.nikestore.databinding.ActivityFavoriteProductBinding
import com.example.nikestore.model.dataclass.Product
import com.example.nikestore.utils.variables.EXTRA_KEY_DATA
import com.example.nikestore.view.detail.ProductDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.view_default_empty_state.*
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteProductActivity : NikeActivity(),
    FavoriteProductAdapter.FavoriteProductEventListener {
    private lateinit var binding:ActivityFavoriteProductBinding

    @Inject
    lateinit var imageLoading: ImageLoading

    private val viewModel:FavoriteProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeData()
    }

    private fun observeData(){
        viewModel.productsLiveData.observe(this){
            if(it.isNullOrEmpty()){
                showEmptyState(R.layout.view_default_empty_state)
                emptyStateMessageTv.text = getString(R.string.favorites_empty_state_message)
            }else{
                setUpUi(it)

            }
        }
    }

    private fun setUpUi(products: List<Product>){
        val favoriteProductAdapter = FavoriteProductAdapter(imageLoading)
        binding.favoriteProductsRv.layoutManager = LinearLayoutManager(this , RecyclerView.VERTICAL ,false)
        favoriteProductAdapter.setProducts(products)
        favoriteProductAdapter.favoriteProductEventListener = this
        binding.favoriteProductsRv.adapter = favoriteProductAdapter

        binding.helpBtn.setOnClickListener {
            showSnackBar(getString(R.string.favorites_help_message))
        }


    }
    override fun onClick(product: Product) {
        startActivity(Intent(this , ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA , product)
        })
    }

    override fun onLongClick(product: Product) {
        viewModel.removeProductFromFavorite(product)
    }
}