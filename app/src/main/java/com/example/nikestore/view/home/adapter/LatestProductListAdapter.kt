package com.example.nikestore.view.home.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.components.imageview.ImageLoading
import com.example.nikestore.databinding.ItemProductBinding
import com.example.nikestore.model.dataclass.Product
import com.example.nikestore.utils.formatPrice
import com.example.nikestore.utils.implementSpringAnimationTrait
import com.example.nikestore.view.home.adapter.common.ProductEventListener
import okhttp3.internal.format
import java.util.ArrayList

class LatestProductListAdapter(val productevent:ProductEventListener, val imageLoading: ImageLoading):RecyclerView.Adapter<LatestProductListAdapter.ViewHolder>() {

    private val productList =ArrayList<Product>()


    fun setProducts(
        products: List<Product>,
    ) {
        this.productList.clear()
        this.productList.addAll(products)
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: ItemProductBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            imageLoading.load(binding.productIv , product.image)
            binding.productTitleTv.text = product.title
            binding.currentPriceTv.text = format(product.price.toString())
            binding.previousPriceTv.text = formatPrice(product.previous_price)
            binding.previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            binding.root.implementSpringAnimationTrait()

            binding.root.setOnClickListener {
            }

            binding.rootProduct.setOnClickListener {
                productevent.onProductClick(product)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context) , parent ,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int  = productList.size



}