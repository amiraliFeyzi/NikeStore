package com.example.nikestore.view.order

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.customview.imageview.NikeImageView
import com.example.nikestore.databinding.ItemOrderHistoryBinding
import com.example.nikestore.model.dataclass.OrderHistoryItem
import com.example.nikestore.utils.convertDpToPixel
import com.example.nikestore.utils.formatPrice
import kotlinx.android.synthetic.main.item_order_history.view.*
import java.util.ArrayList

class OrderHistoryAdapter(private val context:Context):RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {

    val layoutParams: LinearLayout.LayoutParams
    val orderHistoryItems =  ArrayList<OrderHistoryItem>()

    fun setOrderHistoryItem(orderHistoryItems: List<OrderHistoryItem>){
        this.orderHistoryItems.clear()
        this.orderHistoryItems.addAll(orderHistoryItems)
        notifyDataSetChanged()
    }

    init {
        val size = convertDpToPixel(100f, context).toInt()
        val margin = convertDpToPixel(8f, context).toInt()
        layoutParams = LinearLayout.LayoutParams(size, size)
        layoutParams.setMargins(margin, 0, margin, 0)
    }


    inner class ViewHolder(val binding:ItemOrderHistoryBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(orderHistoryItem: OrderHistoryItem){
            binding.orderIdTv.text = orderHistoryItem.id.toString()
            binding.orderAmountTv.text = formatPrice(orderHistoryItem.payable)
            binding.orderProductsLl.removeAllViews()
            orderHistoryItem.order_items.forEach {
                val imageView = NikeImageView(context)
                imageView.layoutParams = layoutParams
                imageView.setImageURI(it.product.image)
                binding.orderProductsLl.addView(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemOrderHistoryBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orderHistoryItems[position])
    }

    override fun getItemCount(): Int  = orderHistoryItems.size
}