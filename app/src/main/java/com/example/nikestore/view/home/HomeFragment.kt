package com.example.nikestore.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.base.NikeFragment
import com.example.nikestore.components.imageview.ImageLoading
import com.example.nikestore.databinding.FragmentHomeBinding
import com.example.nikestore.model.dataclass.Banner
import com.example.nikestore.model.dataclass.Product
import com.example.nikestore.utils.*
import com.example.nikestore.view.detail.ProductDetailActivity
import com.example.nikestore.view.home.adapter.BannerAdapter
import com.example.nikestore.view.home.adapter.LatestProductListAdapter
import com.example.nikestore.view.home.adapter.PopularProductListAdapter
import com.example.nikestore.view.home.adapter.common.ProductEventListener
import com.example.nikestore.view.productlist.ProductListActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : NikeFragment() , ProductEventListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel:HomeViewModel by viewModels()

    @Inject
    lateinit var imageLoading: ImageLoading
    @Inject
    lateinit var bannerAdapter: BannerAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater ,container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
    }

    private fun observeData(){
        viewModel.bannersList.observe(viewLifecycleOwner){
            showSlider(it)
        }

        viewModel.latestProductList.observe(viewLifecycleOwner){
            showListLatestProduct(it)
        }

        viewModel.popularProductList.observe(viewLifecycleOwner){
            showListPopularProduct(it)
        }

        showAllBtn()
    }

    private fun showListLatestProduct(product:List<Product>){
        val latestProductListAdapter = LatestProductListAdapter(this , imageLoading)
        latestProductListAdapter.setProducts(product)
        binding.latestProductRv.layoutManager = LinearLayoutManager(requireContext() , RecyclerView.HORIZONTAL , false)
        binding.latestProductRv.adapter = latestProductListAdapter
    }

    private fun showListPopularProduct(product:List<Product>){
        val popularProductListAdapter = PopularProductListAdapter(imageLoading , this)
        popularProductListAdapter.setProducts(product)
        binding.popularRvProduct.layoutManager = LinearLayoutManager(requireContext() , RecyclerView.HORIZONTAL , false)
        binding.popularRvProduct.adapter = popularProductListAdapter

    }

    private fun showSlider(banners:List<Banner>){
        binding.bannerSliderViewPager.adapter  = bannerAdapter
        bannerAdapter.setBanners(banners)
        binding.sliderIndicator.setViewPager2(binding.bannerSliderViewPager)

        val viewPagerHeight = (((binding.bannerSliderViewPager.measuredWidth - convertDpToPixel(
            32f,
            requireContext()
        )) * 173) / 328).toInt()

        val layoutParams = binding.bannerSliderViewPager.layoutParams
        layoutParams.height = viewPagerHeight
        binding.bannerSliderViewPager.layoutParams = layoutParams
    }

    private fun showAllBtn(){
        binding.viewListProductLatest.setOnClickListener {
            startActivity(Intent(requireContext() , ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_PRODUCT_SORT  , SORT_LATEST)
            })
        }


        binding.viewListProductPopular.setOnClickListener {
            startActivity(Intent(requireContext() , ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_PRODUCT_SORT  , SORT_POPULAR)
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(requireContext() , ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA , product)
        })
    }


}