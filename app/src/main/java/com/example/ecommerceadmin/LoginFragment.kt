package com.example.ecommerceadmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ecommerceadmin.ViewModels.LoginViewModel
import com.example.ecommerceadmin.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLoginBinding.inflate(inflater,container,false)
        loginViewModel.authStateLD.observe(viewLifecycleOwner) {
            if (it == LoginViewModel.AuthState.AUTHENTICATED) {
                findNavController().popBackStack()
            }
        }
        loginViewModel.errMsgLD.observe(viewLifecycleOwner) {
            binding.errMsgTv.text = it
        }
        binding.loginBtn.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            // TODO: validate fields
            loginViewModel.loginAdmin(email, password)
        }

        return binding.root
    }
}