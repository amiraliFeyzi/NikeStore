package com.example.nikestore.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.nikestore.R
import com.example.nikestore.databinding.FragmentLogInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_log_in.*
@AndroidEntryPoint
class LogInFragment : Fragment() {

    private var _binding: FragmentLogInBinding?=null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogInBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginBtn.setOnClickListener {
            viewModel.login(binding.emailEt.text.toString() , binding.passwordEt.text.toString())
            requireActivity().finish()
        }
        //login()
        signUpLinkBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.frame_auth, SignUpFragment())
            }.commit()
        }
    }


//    fun login(){
//        loginBtn.setOnClickListener {
//            viewModel.login(binding.emailEt.text.toString() , binding.passwordEt.text.toString())
//        }
//    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}