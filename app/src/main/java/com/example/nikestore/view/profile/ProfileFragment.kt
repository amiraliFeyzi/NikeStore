package com.example.nikestore.view.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.nikestore.R
import com.example.nikestore.databinding.FragmentProfileBinding
import com.example.nikestore.view.auth.AuthActivity
import com.example.nikestore.view.favorite.FavoriteProductActivity
import com.example.nikestore.view.favorite.FavoriteProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding:FragmentProfileBinding? = null
    private val binding get() =  _binding!!

    private val viewModel:ProfileViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater , container ,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoriteProductsBtn.setOnClickListener {
            startActivity(Intent(requireContext() , FavoriteProductActivity::class.java))
        }
    }


    override fun onResume() {
        super.onResume()
        checkAuthState()
    }

    private fun checkAuthState(){
        if (viewModel.isSignedIn){
            binding.authBtn.text = getString(R.string.signOut)
            binding.authBtn.setCompoundDrawablesWithIntrinsicBounds( 0 , 0 , R.drawable.ic_sign_out , 0)
            binding.usernameTv.text = viewModel.username
            binding.authBtn.setOnClickListener {
                viewModel.signOut()
                checkAuthState()
            }
        }else{
            binding.authBtn.text = getString(R.string.signIn)
            binding.authBtn.setOnClickListener {
                startActivity(Intent(requireContext() , AuthActivity::class.java))
            }
            binding.authBtn.setCompoundDrawablesWithIntrinsicBounds( 0 , 0 , R.drawable.ic_sign_in , 0)
            binding.usernameTv.text = getString(R.string.guest_user)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding  = null
    }

}