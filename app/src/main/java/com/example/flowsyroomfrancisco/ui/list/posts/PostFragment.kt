package com.example.flowsyroomfrancisco.ui.list.posts

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flowsyroomfrancisco.databinding.FragmentPostBinding
import com.example.flowsyroomfrancisco.domain.model.Post
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostFragment: Fragment() {
    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!

    private lateinit var postsAdapter: PostsAdapter

    private val viewModel: PostViewModel by viewModels()


    //necesario para el id que se manda desde blogs
    val args: PostFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsAdapter = PostsAdapter(requireContext(), object : PostsAdapter.PostsActions{
            override fun itemHasClicked(post: Post) {
                //TODO IMPLEMENT METHOD
            }
        })

        with(binding){
            postRecycleView.adapter = postsAdapter
            postRecycleView.layoutManager= LinearLayoutManager(requireContext())
        }

        viewModel.handleEvent(PostEvent.GetAllPostsByBlogId(args.blogId))

        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect(){value ->
                    value.posts?.let {
                        if (it.isNotEmpty()){
                            postsAdapter.submitList(it)
                        }
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
                    val asd = it
                    Toast.makeText(requireContext(), asd, Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}