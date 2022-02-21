package com.example.nikestore.view.productlist

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nikestore.R
import com.example.nikestore.base.NikeActivity
import com.example.nikestore.databinding.ActivityProductListBinding
import com.example.nikestore.model.dataclass.Product
import com.example.nikestore.utils.variables.EXTRA_KEY_DATA
import com.example.nikestore.utils.variables.VIEW_TYPE_LARGE
import com.example.nikestore.utils.variables.VIEW_TYPE_SMALL
import com.example.nikestore.view.detail.ProductDetailActivity
import com.example.nikestore.view.home.adapter.common.ProductEventListener
import com.example.nikestore.view.productlist.adapter.ProductListAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductListActivity : NikeActivity() , ProductEventListener {
    lateinit var binding:ActivityProductListBinding
    private val viewModel:ProductListViewModel by viewModels()

    @Inject
    lateinit var productListAdapter: ProductListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeData()



    }

    private fun observeData(){
        viewModel.productLiveData.observe(this){
            setUpUi(it)
        }

        viewModel.selectedSortLiveData.observe(this){
            binding.selectedSortTitleTv.text = getString(it)
        }
    }


    private fun setUpUi(products:List<Product>){
        val gridLayoutManager = GridLayoutManager(this , 2)
        productListAdapter.setViewType(VIEW_TYPE_SMALL)
        productListAdapter.setProducts(products)
        binding.productsRv.layoutManager = gridLayoutManager
        binding.productsRv.adapter = productListAdapter

        binding.viewTypeChangerBtn.setOnClickListener {
            if (productListAdapter.getViewTpe() == VIEW_TYPE_SMALL){
                binding.viewTypeChangerBtn.setImageResource(R.drawable.ic_view_type_large)
                productListAdapter.setViewType(VIEW_TYPE_LARGE)
                gridLayoutManager.spanCount = 1
                productListAdapter.notifyDataSetChanged()
            }else{
                binding.viewTypeChangerBtn.setImageResource(R.drawable.ic_grid)
                productListAdapter.setViewType(VIEW_TYPE_SMALL)
                gridLayoutManager.spanCount = 2
                productListAdapter.notifyDataSetChanged()
            }
        }

        productListAdapter.productEventListener = this

        binding.toolbarView.onBackButtonClickListener = View.OnClickListener {
            finish()
        }
        changeSort()


    }


    private fun changeSort(){
        binding.sortBtn.setOnClickListener {
            val dialog =MaterialAlertDialogBuilder(this)
                .setSingleChoiceItems(R.array.sortTitlesArray , viewModel.getSortFromSavedStateHandle().toInt() , object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, sortSelected: Int) {
                        dialog?.dismiss()
                        viewModel.onSelectedSortChangeByUser(sortSelected)
                    }

                }).setTitle(getString(R.string.sort))

            dialog.show()
        }
    }
    override fun onProductClick(product: Product) {
        startActivity(Intent(this , ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA , product)
        })
    }
}