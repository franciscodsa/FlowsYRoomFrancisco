package com.example.flowsyroomfrancisco.ui.list.blogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowsyroomfrancisco.domain.model.Blog
import com.example.flowsyroomfrancisco.domain.usecases.CreateBlogUsecase
import com.example.flowsyroomfrancisco.domain.usecases.DeleteBlogUsecase
import com.example.flowsyroomfrancisco.domain.usecases.GetAllBlogsUsecase
import com.example.flowsyroomfrancisco.utils.NetworkResultt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogsViewModel @Inject constructor(
    private val getAllBlogsUsecase: GetAllBlogsUsecase,
    private val createBlogUsecase: CreateBlogUsecase,
    private val deleteBlogUsecase: DeleteBlogUsecase
): ViewModel() {

    private val _uiState: MutableStateFlow<BlogsState> by lazy {
        MutableStateFlow(BlogsState())
    }
    val uiState: StateFlow<BlogsState> = _uiState

    private val _uiError = Channel<String>()
    val uiError = _uiError.receiveAsFlow()

    fun handleEvent(event: BlogsEvent){
        when(event){
            BlogsEvent.GetAllBlogs -> getAllBlogs()
            is BlogsEvent.CreateBlog -> createBlog(event.blog)
            is BlogsEvent.DeleteBlog -> deleteBlog(event.blogId)
        }
    }

    private fun getAllBlogs() {
        viewModelScope.launch {
            getAllBlogsUsecase.invoke()
                .catch(action = {cause -> _uiError.send(cause.message ?: "")  })
                .collect {result ->
                    when(result){
                        is NetworkResultt.Error -> _uiState.update {
                            it.copy(
                                message = result.message,
                                isLoading = false
                            )
                        }
                        is NetworkResultt.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is NetworkResultt.Success -> _uiState.update {
                            it.copy(
                                blogs = result.data,
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }

    private fun createBlog(blog: Blog) {
        viewModelScope.launch {
            createBlogUsecase(blog)
                .catch(action = { cause -> _uiError.send(cause.message ?: "") })
                .collect { result ->
                    when(result) {
                        is NetworkResultt.Error -> _uiError.send(result.message ?: "")
                        is NetworkResultt.Loading ->  _uiState.update { it.copy(isLoading = true) }
                        is NetworkResultt.Success -> {
                            getAllBlogs()
                        }
                    }
                }
        }
    }

    private fun deleteBlog(blogId: Int) {
        viewModelScope.launch {
            deleteBlogUsecase(blogId)
                .catch(action = { cause -> _uiError.send(cause.message ?: "") })
                .collect { result ->
                    when(result) {
                        is NetworkResultt.Error -> _uiError.send(result.message ?: "")
                        is NetworkResultt.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is NetworkResultt.Success -> {
                            getAllBlogs()
                        }
                    }
                }
        }
    }
}