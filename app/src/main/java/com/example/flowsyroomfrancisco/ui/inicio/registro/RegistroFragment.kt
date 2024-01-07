package com.example.flowsyroomfrancisco.ui.inicio.registro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flowsyroomfrancisco.databinding.FragmentLoginBinding
import com.example.flowsyroomfrancisco.databinding.FragmentRegistroBinding
import com.example.flowsyroomfrancisco.ui.inicio.login.LoginFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistroFragment: Fragment() {
    private var _binding: FragmentRegistroBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            registroRegistrarseBttn.setOnClickListener{
                //TODO : Realiza la comprobacion de las password y envia la peticion de registro
            }
        }
    }
}