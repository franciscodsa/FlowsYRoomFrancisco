package com.example.flowsyroomfrancisco.ui.list.blogs

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flowsyroomfrancisco.databinding.FragmentBlogsBinding
import com.example.flowsyroomfrancisco.domain.model.Blog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BlogsFragment : Fragment() {
    private var _binding: FragmentBlogsBinding? = null
    private val binding get() = _binding!!

    private lateinit var blogsAdapter: BlogsAdapter

    private val viewModel: BlogsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBlogsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        blogsAdapter = BlogsAdapter(requireContext(), object : BlogsAdapter.BlogsActions{
            override fun itemHasClicked(blog: Blog) {
                val action = BlogsFragmentDirections.actionBlogsFragmentToPostFragment(blog.id)
                findNavController().navigate(action)
            }
        })

        binding.blogRecycleView.adapter = blogsAdapter
        binding.blogRecycleView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.handleEvent(BlogsEvent.GetAllBlogs)

        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect(){value ->
                    value.blogs?.let {
                        if (it.isNotEmpty()){
                            blogsAdapter.submitList(it)
                        }
                    }

                    value.message?.let {
                        Toast.makeText(this@BlogsFragment.context, it, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiError.collect {
                    val asd = it
                    Toast.makeText(requireContext(), asd, Toast.LENGTH_LONG).show()
                }
            }
        }

    }


}