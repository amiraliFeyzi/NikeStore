package com.example.nikestore.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.nikestore.R
import com.example.nikestore.base.NikeActivity
import com.example.nikestore.databinding.ActivityMainBinding
import com.example.nikestore.model.dataclass.CartItemCount
import com.example.nikestore.utils.convertDpToPixel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.color.MaterialColors
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : NikeActivity() {
    private lateinit var binding:ActivityMainBinding

    private val viewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCartItemsCount()
    }
    private fun setUpBottomNavigation(){
        val navHostFragment:NavHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController:NavController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigation , navController)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCartItemsCountChange(cartItemCount: CartItemCount){
        val badge = binding.bottomNavigation.getOrCreateBadge(R.id.cartFragment)
        badge.apply {
            badgeGravity = BadgeDrawable.BOTTOM_START
            backgroundColor = MaterialColors.getColor(binding.bottomNavigation ,
                androidx.appcompat.R.attr.colorPrimary)
            number = cartItemCount.count
            verticalOffset = convertDpToPixel(10f , this@MainActivity).toInt()

        }
        badge.isVisible = cartItemCount.count>0

    }

}