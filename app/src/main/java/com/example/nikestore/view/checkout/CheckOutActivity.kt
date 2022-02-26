package com.example.nikestore.view.checkout

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.nikestore.databinding.ActivityCheckOutBinding
import com.example.nikestore.utils.formatPrice
import com.example.nikestore.utils.variables.EXTRA_KEY_DATA_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_check_out.*
import kotlinx.android.synthetic.main.item_purchase_details.*

@AndroidEntryPoint
class CheckOutActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCheckOutBinding

    private val viewModel:CheckOutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uri: Uri? = intent.data
        var orderId:Int? = null
        if (uri !=null){
            orderId =uri.getQueryParameter("order_id")!!.toInt()
        }else{
            orderId = intent.extras?.getInt(EXTRA_KEY_DATA_ID)
        }


        viewModel.checkOut(orderId!!)
        viewModel.checkOutLiveData.observe(this){
            orderPriceTv.text = formatPrice(it.payable_price)
            orderStatusTv.text = it.payment_status
            purchaseStatusTv.text = if (it.purchase_success) "خرید با موفقیت انجام شد" else "خرید ناموفق بود"

        }

        binding.toolbarView.onBackButtonClickListener =View.OnClickListener {
            finish()
        }


    }


}