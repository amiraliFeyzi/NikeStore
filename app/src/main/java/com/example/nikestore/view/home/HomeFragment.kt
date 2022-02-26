package com.example.nikestore.view.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.base.NikeFragment
import com.example.nikestore.components.imageview.ImageLoading
import com.example.nikestore.databinding.FragmentHomeBinding
import com.example.nikestore.model.dataclass.Banner
import com.example.nikestore.model.dataclass.Product
import com.example.nikestore.utils.*
import com.example.nikestore.utils.variables.EXTRA_KEY_DATA
import com.example.nikestore.utils.variables.EXTRA_KEY_PRODUCT_SORT
import com.example.nikestore.utils.variables.SORT_LATEST
import com.example.nikestore.utils.variables.SORT_POPULAR
import com.example.nikestore.view.detail.ProductDetailActivity
import com.example.nikestore.view.home.adapter.BannerAdapter
import com.example.nikestore.view.home.adapter.LatestProductListAdapter
import com.example.nikestore.view.home.adapter.PopularProductListAdapter
import com.example.nikestore.view.home.adapter.SearchAdapter
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


    @Inject
    lateinit var latestProductListAdapter: LatestProductListAdapter

    @Inject
    lateinit var popularProductListAdapter: PopularProductListAdapter



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

        searchProduct()
        showAllBtnClick()
    }

    private fun searchProduct(){
        binding.searchRv.layoutManager = GridLayoutManager(requireContext() , 2)
        val searchAdapter = SearchAdapter(imageLoading)
        searchAdapter.productEventListener = this

        binding.etSearch.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                changeVisibilityForSearch()
                viewModel.searchProduct(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        viewModel.responseSearchProduct.observe(viewLifecycleOwner){
            searchAdapter.setProducts(it)
            binding.searchRv.adapter = searchAdapter
        }
    }

    private fun changeVisibilityForSearch(){
        binding.frameViewPager.visibility = View.GONE
        binding.frameText.visibility = View.GONE
        binding.frameText2.visibility = View.GONE
        binding.latestProductRv.visibility = View.GONE
        binding.popularRvProduct.visibility = View.GONE
        binding.searchRv.visibility = View.VISIBLE
    }

    private fun showListLatestProduct(product:List<Product>){
        latestProductListAdapter.setProducts(product)
        latestProductListAdapter.productEventListener = this
        binding.latestProductRv.layoutManager = LinearLayoutManager(requireContext() , RecyclerView.HORIZONTAL , false)
        binding.latestProductRv.adapter = latestProductListAdapter
    }

    private fun showListPopularProduct(product:List<Product>){
        popularProductListAdapter.setProducts(product)
        popularProductListAdapter.productEventListener  = this
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

    private fun showAllBtnClick(){
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

    override fun onFavoriteBtnClick(product: Product) {
        viewModel.addProductToFavorite(product)
    }


}