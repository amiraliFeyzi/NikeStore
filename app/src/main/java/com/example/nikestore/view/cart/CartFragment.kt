package com.example.nikestore.view.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.base.NikeFragment
import com.example.nikestore.databinding.FragmentCartBinding
import com.example.nikestore.model.dataclass.CartItem
import com.example.nikestore.model.dataclass.PurchaseDetail
import com.example.nikestore.utils.variables.EXTRA_KEY_DATA
import com.example.nikestore.view.auth.AuthActivity

import com.example.nikestore.view.detail.ProductDetailActivity
import com.example.nikestore.view.shipping.ShippingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.view_cart_empty_state.*
import kotlinx.android.synthetic.main.view_cart_empty_state.view.*
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : NikeFragment(),CartAdapter.CartItemViewCallbacks {

    private var _binding:FragmentCartBinding? = null
    private val binding get() =  _binding!!

    private val viewModel:CartViewModel by viewModels()

    @Inject
    lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.refresh()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()

    }

    fun observeData(){
        viewModel.cartItemsLiveData.observe(viewLifecycleOwner){
           setUpCartItem(it)
        }

        viewModel.purchaseDetailLiveData.observe(viewLifecycleOwner){
            setUpPurchaseDetail(it)
        }

        viewModel.emptyStateCart.observe(viewLifecycleOwner){
            if (it.mustShow){
                val emptyState = showEmptyState(R.layout.view_cart_empty_state)
                emptyState?.let{view->
                    view.emptyStateMessageTv.text = getString(it.messageResId)
                    view.emptyStateCtaBtn.visibility = if (it.mustShowCallToActionButton) View.VISIBLE else View.GONE
                    view.emptyStateCtaBtn.setOnClickListener {
                        startActivity(Intent(requireContext() , AuthActivity::class.java))
                    }
                }
            }else{
                emptyStateRootView?.visibility = View.GONE
            }
        }

        paymentProducts()
    }

    fun paymentProducts(){
        binding.payBtn.setOnClickListener {
            startActivity(Intent(requireContext()  , ShippingActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA , viewModel.purchaseDetailLiveData.value)
            })
        }
    }
    private fun setUpCartItem(cartItems: List<CartItem>){

        cartAdapter.cartItemViewCallbacks = this
        binding.cartItemsRv.layoutManager = LinearLayoutManager(requireContext() , RecyclerView.VERTICAL , false)
        cartAdapter.setCartItems(cartItems)
        binding.cartItemsRv.adapter = cartAdapter
    }

    private fun setUpPurchaseDetail(purchaseDetail: PurchaseDetail){
        cartAdapter.purchaseDetail = purchaseDetail
        cartAdapter.notifyItemChanged(cartAdapter.cartItems.size)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onRemoveCartItemButtonClick(cartItem: CartItem) {
        try {
            viewModel.removeItemFromCart(cartItem)
        }finally {
            cartAdapter.removeCartItem(cartItem)
        }
    }

    override fun onIncreaseCartItemButtonClick(cartItem: CartItem) {
        try {
            viewModel.increaseCartItemCount(cartItem)
        }finally {
            cartAdapter.increaseCount(cartItem)
        }
    }

    override fun onDecreaseCartItemButtonClick(cartItem: CartItem) {
        try {
            viewModel.decreaseCartItemCount(cartItem)
        }finally {
            cartAdapter.decreaseCount(cartItem)
        }
    }

    override fun onProductImageClick(cartItem: CartItem) {
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, cartItem.product)
        })
    }
}