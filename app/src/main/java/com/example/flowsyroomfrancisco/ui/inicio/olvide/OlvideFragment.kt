package com.example.flowsyroomfrancisco.ui.inicio.olvide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flowsyroomfrancisco.databinding.FragmentLoginBinding
import com.example.flowsyroomfrancisco.databinding.FragmentOlvideBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OlvideFragment: Fragment() {
    private var _binding: FragmentOlvideBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOlvideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            passwordOlvidadaBttnRequest.setOnClickListener {
                //TODO: envia peticion que enviara contraseña temporal al email proporcionado por el usuario
            }
        }
    }
}