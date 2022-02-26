package com.example.nikestore.view.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.databinding.ActivityOrderHistoryBinding
import com.example.nikestore.model.dataclass.OrderHistoryItem
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class OrderHistoryActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOrderHistoryBinding
    private val viewModel:OrderHistoryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeData()
    }

    private fun observeData(){
        viewModel.ordersLiveData.observe(this){
            Timber.i(it.toString())
            setUpUi(it)
        }

    }

    private fun setUpUi(orders:List<OrderHistoryItem>){
        val orderHistoryAdapter = OrderHistoryAdapter(this)
        orderHistoryAdapter.setOrderHistoryItem(orders)
        binding.orderHistoryRv.layoutManager = LinearLayoutManager(this ,RecyclerView.VERTICAL ,false)
        binding.orderHistoryRv.adapter = orderHistoryAdapter

        binding.toolbarView.onBackButtonClickListener = View.OnClickListener {
            finish()
        }
    }
}