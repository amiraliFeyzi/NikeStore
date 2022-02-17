package com.example.nikestore.view.productlist.adapter

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
import com.example.nikestore.utils.*
import com.example.nikestore.view.home.adapter.common.ProductEventListener
import javax.inject.Inject


class ProductListAdapter @Inject constructor(
    val imageLoading: ImageLoading
) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    var productEventListener: ProductEventListener? = null

    private var viewType: Int = VIEW_TYPE_SMALL

    fun setViewType(viewType: Int,) {
        this.viewType = viewType
        notifyDataSetChanged()
    }
    var products = ArrayList<Product>()

    fun setProducts(products:List<Product>){
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    fun getViewTpe():Int{
        return viewType
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


        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutResId = when (viewType) {
            VIEW_TYPE_SMALL -> R.layout.item_product_small
            VIEW_TYPE_LARGE -> R.layout.item_product_large
            else -> throw IllegalStateException("viewType is not valid")
        }
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindProduct(products[position])

    override fun getItemCount(): Int = products.size

}
