package com.example.nikestore.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.nikestore.R
import com.example.nikestore.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_sign_up.*
@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var _binding:FragmentSignUpBinding?=null
    private val binding get() = _binding!!

    private val viewModel:AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater ,container ,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            viewModel.signUp(email  , password)

            viewModel.finishWorkLiveData.observe(viewLifecycleOwner){
                if (it){
                    requireActivity().finish()
                }
            }

        }

        loginLinkBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.frame_auth, LogInFragment())
            }.commit()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}