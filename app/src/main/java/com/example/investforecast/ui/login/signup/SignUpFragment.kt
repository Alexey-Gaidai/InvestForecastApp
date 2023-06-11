package com.example.investforecast.ui.login.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.investforecast.App
import com.example.investforecast.R
import com.example.investforecast.databinding.FragmentSignInBinding
import com.example.investforecast.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val model: SignUpViewModel by viewModels { SignUpViewModelFactory(App.repository) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        register()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observeViewModel() {
        model.response.observe(viewLifecycleOwner){
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun register() {
        binding.btRegister.setOnClickListener {
            if (binding.etPassword.text.toString() == binding.etRepeatPassword.text.toString() && binding.cbAgree.isChecked && binding.etName.text.isNotEmpty() && binding.etEmail.text.isNotEmpty()) {
                model.signUp(
                    binding.etName.text.toString(),
                    binding.etPassword.text.toString(),
                    binding.etEmail.text.toString(),
                )
            }
        }
    }
}