package com.example.flowsyroomfrancisco.ui.inicio.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flowsyroomfrancisco.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment() {
    private var _binding: FragmentLoginBinding?= null
    private val binding get() = _binding!!

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

        with(binding){
            registrarseBttn.setOnClickListener{
                val action = LoginFragmentDirections.actionLoginFragmentToRegistroFragment()
                findNavController().navigate(action)
            }

            loginBttn.setOnClickListener{
                //TODO: envia peticion de login y con esto probablemente se tenga que hacer algo para establecer el token en el interceptor de llamadas
            }

            passwordOlvidadaBttn.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToOlvideFragment()
                findNavController().navigate(action)
            }
        }
    }
}