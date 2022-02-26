package com.example.nikestore.view.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.components.imageview.ImageLoading
import com.example.nikestore.databinding.ItemFavoriteProductBinding
import com.example.nikestore.model.dataclass.Product
import java.util.ArrayList
import javax.inject.Inject

class FavoriteProductAdapter @Inject constructor(private val imageLoading: ImageLoading)  :RecyclerView.Adapter<FavoriteProductAdapter.ViewHolder>() {

    var favoriteProductEventListener:FavoriteProductEventListener?=null

    private val productList = ArrayList<Product>()
    fun setProducts(products:List<Product>){
        productList.clear()
        productList.addAll(products)
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding:ItemFavoriteProductBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            binding.productTitleTv.text = product.title
            imageLoading.load(binding.nikeImageView , product.image)

            binding.root.setOnClickListener {
                favoriteProductEventListener?.onClick(product)
            }

            binding.root.setOnLongClickListener {
                productList.remove(product)
                notifyItemRemoved(adapterPosition)
                favoriteProductEventListener?.onLongClick(product)
                return@setOnLongClickListener false
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(ItemFavoriteProductBinding.inflate(LayoutInflater.from(parent.context)  , parent , false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(productList[position])
    }

    override fun getItemCount(): Int  = productList.size

    interface FavoriteProductEventListener {
        fun onClick(product: Product)
        fun onLongClick(product: Product)
    }
}