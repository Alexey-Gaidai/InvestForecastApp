package com.example.investforecast.ui.login.signin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.investforecast.R
import com.example.investforecast.databinding.FragmentSignInBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val model: SignInViewModel by viewModels()
    private var loginSuccessListener: OnLoginSuccessListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLoginSuccessListener) {
            loginSuccessListener = context
        } else {
            throw RuntimeException("$context must implement OnLoginSuccessListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToSignUp()
        observeViewModel()
        login()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observeViewModel() {
        model.isTokenNotEmpty.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                Snackbar.make(binding.root, "Вход выполнен", Snackbar.LENGTH_LONG).show()
            }
            loginSuccessListener?.onLoginSuccess()
        }
    }

    private fun login() {
        binding.btLogin.setOnClickListener {
            model.login(binding.etLogin.text.toString(), binding.etPassword.text.toString())
        }
    }

    private fun navigateToSignUp() {
        binding.btSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }
}

public interface OnLoginSuccessListener {
    fun onLoginSuccess()
}