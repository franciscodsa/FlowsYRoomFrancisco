package com.example.flowsyroomfrancisco.ui.inicio.login

import android.content.Intent
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
import com.example.flowsyroomfrancisco.ui.list.SecondActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observe()
    }

    private fun setupUI() {
        with(binding) {
            registrarseBttn.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToRegistroFragment()
                findNavController().navigate(action)
            }

            loginBttn.setOnClickListener {
                //TODO: envía petición de login y establece el token en el interceptor de llamadas
                val email: String = loginTextEmailAddress.text.toString()
                val password: String = loginTextNumberPassword.text.toString()
                viewModel.handleEvent(LoginEvent.Login(email, password))
            }

            //esta dentro de una corutina porque a veces la navegacion se ejecutaba antes que el evento y la app se cerraba
            passwordOlvidadaBttn.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.handleEvent(
                        LoginEvent.OlvidePassword(
                            editTextTextEmailAddressOlvideContraseA.text.toString()
                        )
                    )
                    val action = LoginFragmentDirections.actionLoginFragmentToOlvideFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    val tempPass = value.isTempPassword
                    if (value.logged && !tempPass) {
                        // Logged in and not a temporary password
                        Toast.makeText(
                            requireContext(),
                            "Bienvenido!",
                            Toast.LENGTH_LONG
                        ).show()

                        val intent = Intent(this@LoginFragment.context, SecondActivity::class.java)
                        startActivity(intent)
                    } else if (value.logged && value.isTempPassword) {
                        // Logged in with temporary password, navigate to change password fragment
                        val action =
                            LoginFragmentDirections.actionLoginFragmentToOlvideFragment()
                        findNavController().navigate(action)
                    }

                    value.message?.let {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                    }
                }

            }

        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiError.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}
