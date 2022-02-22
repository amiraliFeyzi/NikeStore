package com.example.nikestore.view.shipping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.nikestore.R
import com.example.nikestore.databinding.ActivityShippingBinding
import com.example.nikestore.model.dataclass.PurchaseDetail
import com.example.nikestore.utils.openUrlInCustomTab
import com.example.nikestore.utils.variables.EXTRA_KEY_DATA
import com.example.nikestore.utils.variables.EXTRA_KEY_DATA_ID
import com.example.nikestore.utils.variables.PAYMENT_METHOD_COD
import com.example.nikestore.utils.variables.PAYMENT_METHOD_ONLINE
import com.example.nikestore.view.cart.CartAdapter
import com.example.nikestore.view.checkout.CheckOutActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_shipping.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ShippingActivity : AppCompatActivity() {
    private lateinit var binding:ActivityShippingBinding

    private val viewModel:ShippingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShippingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setUpUi()

        payment()


    }


    private fun setUpUi(){
        val purchaseDetail = intent.extras?.getParcelable<PurchaseDetail>(EXTRA_KEY_DATA)

        val viewHolder = CartAdapter.PurchaseDetailViewHolder(purchaseDetailView)

        viewHolder.bind(purchaseDetail!!.totalPrice , purchaseDetail.shipping_cost , purchaseDetail.payable_price)
    }


    private fun payment(){
        val onClickListener = View.OnClickListener {
            viewModel.submit(binding.firstNameEt.text.toString() , binding.lastNameEt.text.toString() ,
                binding.postalCodeEt.text.toString() ,
                binding.phoneNumberEt.text.toString() ,
                binding.addressEt.text.toString() ,
                if (it.id == R.id.onlinePaymentBtn) PAYMENT_METHOD_ONLINE else PAYMENT_METHOD_COD)

            viewModel.submitLiveData.observe(this) {
                if (it.bank_gateway_url.isNotEmpty()){
                    openUrlInCustomTab(this@ShippingActivity , it.bank_gateway_url)
                    finish()
                }else{
                    startActivity(Intent(this@ShippingActivity , CheckOutActivity::class.java).apply {
                        putExtra(EXTRA_KEY_DATA_ID , it.order_id)
                    })
                    finish()
                }
            }

        }
        binding.onlinePaymentBtn.setOnClickListener(onClickListener)
        binding.codBtn.setOnClickListener(onClickListener)
    }
}