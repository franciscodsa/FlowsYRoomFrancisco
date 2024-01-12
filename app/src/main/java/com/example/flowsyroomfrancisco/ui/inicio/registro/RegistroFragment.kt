package com.example.flowsyroomfrancisco.ui.inicio.registro

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
import com.example.flowsyroomfrancisco.databinding.FragmentRegistroBinding
import com.example.flowsyroomfrancisco.ui.inicio.login.LoginFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistroFragment: Fragment() {
    private var _binding: FragmentRegistroBinding?= null
    private val binding get() = _binding!!

    private val viewModel: RegistroViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)

        with(binding){
            registroRegistrarseBttn.setOnClickListener{
                val password = binding.registroTextNumberPassword.text.toString()
                val confirmPassword = binding.editTextTextPassword.text.toString()
                if (password == confirmPassword){
                    viewModel.handleEvent(RegistroEvent.Register(binding.registroTextEmailAddress.text.toString(), password))
                }else{
                    Toast.makeText(this@RegistroFragment.context, "contraseñas no coinciden", Toast.LENGTH_LONG).show()
                }


            }
        }

        observe()
        return binding.root
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    value.result?.let {
                        if (it) {
                            Toast.makeText(
                                this@RegistroFragment.context,
                                it.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                            val action =
                                LoginFragmentDirections.actionLoginFragmentToRegistroFragment()
                            findNavController().navigate(action)
                        }
                    }

                    value.message?.let {

                        Toast.makeText(this@RegistroFragment.context, it, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiError.collect {
                    Toast.makeText(this@RegistroFragment.context, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}