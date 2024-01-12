package com.example.flowsyroomfrancisco.ui.inicio.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.flowsyroomfrancisco.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment: Fragment() {
    private var _binding: FragmentLoginBinding?= null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        with(binding){
            registrarseBttn.setOnClickListener{
                val action = LoginFragmentDirections.actionLoginFragmentToRegistroFragment()
                findNavController().navigate(action)
            }

            loginBttn.setOnClickListener{
                //TODO: envia peticion de login y con esto probablemente se tenga que hacer algo para establecer el token en el interceptor de llamadas
                val email: String = loginTextEmailAddress.text.toString()
                val password: String = loginTextNumberPassword.text.toString()
                viewModel.handleEvent(LoginEvent.Login(email, password))
            }

            passwordOlvidadaBttn.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToOlvideFragment()
                findNavController().navigate(action)
            }
        }

        observe()

        return binding.root


    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    value.logged?.let {
                        if (it) {
                            Toast.makeText(
                                this@LoginFragment.context,
                                it.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                            val action =
                                LoginFragmentDirections.actionLoginFragmentToRegistroFragment()
                            findNavController().navigate(action)
                        }
                    }

                    value.message?.let {

                        Toast.makeText(this@LoginFragment.context, it, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiError.collect {
                    binding.editTextTextMultiLine.setText(it)
                    Toast.makeText(this@LoginFragment.context, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}