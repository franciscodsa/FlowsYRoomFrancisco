package com.example.flowsyroomfrancisco.ui.inicio.olvide

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
import com.example.flowsyroomfrancisco.databinding.FragmentOlvideBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OlvideFragment : Fragment() {
    private var _binding: FragmentOlvideBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OlvideViewModel by viewModels()

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
        observe()
        setupUI()
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    if (value.changed) {
                        Toast.makeText(
                            requireContext(),
                            "Contraseña cambiada exitosamente",
                            Toast.LENGTH_LONG
                        ).show()
                        val action = OlvideFragmentDirections.actionOlvideFragmentToLoginFragment()
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

    private fun setupUI() {
        with(binding) {
            passwordOlvidadaBttnRequest.setOnClickListener {
                with(binding){
                    val email = editTextEmailAddressChange.text.toString()
                    val password = editTextOldPassword.text.toString()
                    val confirmPassword = editTextNewPassword.text.toString()

                    viewModel.handleEvent(OlvideEvent.ChangerPassword(email, password, confirmPassword))
                    /*val action = OlvideFragmentDirections.actionOlvideFragmentToLoginFragment()
                    findNavController().navigate(action)*/
                }
            }
        }
    }
}