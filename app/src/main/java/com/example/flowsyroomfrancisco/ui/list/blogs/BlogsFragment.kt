package com.example.flowsyroomfrancisco.ui.list.blogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flowsyroomfrancisco.databinding.FragmentBlogsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlogsFragment : Fragment() {
    private var _binding: FragmentBlogsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBlogsBinding.inflate(inflater, container, false)
        return binding.root
    }


}