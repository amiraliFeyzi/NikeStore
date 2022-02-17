package com.example.nikestore.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.components.imageview.ImageLoading
import com.example.nikestore.databinding.ItemBannerBinding
import com.example.nikestore.model.dataclass.Banner
import java.util.ArrayList
import javax.inject.Inject

class BannerAdapter @Inject constructor(val imageLoading: ImageLoading):RecyclerView.Adapter<BannerAdapter.ViewHolder>() {

    private var bannerList = ArrayList<Banner>()


    fun setBanners(
        banner: List<Banner>,
    ) {
        this.bannerList.clear()
        this.bannerList.addAll(banner)
        notifyDataSetChanged()
    }
    inner class ViewHolder(val binding:ItemBannerBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(banner: Banner){
            imageLoading.load(binding.ivBanner , banner.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent , false) )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bannerList[position])
    }

    override fun getItemCount(): Int = bannerList.size
}