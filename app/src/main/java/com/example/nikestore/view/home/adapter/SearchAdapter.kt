package com.example.nikestore.view.home.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.components.imageview.ImageLoading
import com.example.nikestore.customview.imageview.NikeImageView
import com.example.nikestore.model.dataclass.Product
import com.example.nikestore.utils.formatPrice
import com.example.nikestore.utils.implementSpringAnimationTrait
import com.example.nikestore.utils.variables.VIEW_TYPE_LARGE
import com.example.nikestore.utils.variables.VIEW_TYPE_SMALL
import com.example.nikestore.view.home.adapter.common.ProductEventListener
import com.example.nikestore.view.productlist.adapter.ProductListAdapter
import kotlinx.android.synthetic.main.item_product.view.*
import javax.inject.Inject

class SearchAdapter @Inject constructor(
    val imageLoading: ImageLoading
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    var productEventListener: ProductEventListener? = null

    var products = ArrayList<Product>()
    fun setProducts(products:List<Product>){
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productIv: NikeImageView = itemView.findViewById(R.id.productIv)
        val titleTv: TextView = itemView.findViewById(R.id.productTitleTv)
        val currentPriceTv: TextView = itemView.findViewById(R.id.currentPriceTv)
        val previousPriceTv: TextView = itemView.findViewById(R.id.previousPriceTv)
        val favoriteBtn: ImageView = itemView.findViewById(R.id.favoriteBtn)
        fun bindProduct(product: Product) {
            imageLoading.load(productIv, product.image)
            titleTv.text = product.title
            currentPriceTv.text = formatPrice(product.price)
            previousPriceTv.text = formatPrice(product.previous_price)
            previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.implementSpringAnimationTrait()

            itemView.setOnClickListener {
                productEventListener?.onProductClick(product)
            }


            if (product.isFavorite){
                favoriteBtn.setImageResource(R.drawable.ic_favorite_fill)
            }else{
                itemView.favoriteBtn.setImageResource(R.drawable.ic_favorites)
            }

            favoriteBtn.setOnClickListener {
                productEventListener?.onFavoriteBtnClick(product)
                product.isFavorite = !product.isFavorite
                notifyItemChanged(adapterPosition)
            }



        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product , parent , false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =  holder.bindProduct(products[position])

    override fun getItemCount(): Int = products.size

}