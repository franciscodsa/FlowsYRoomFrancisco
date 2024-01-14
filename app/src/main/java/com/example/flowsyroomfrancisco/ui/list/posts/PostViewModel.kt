package com.example.flowsyroomfrancisco.ui.list.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowsyroomfrancisco.domain.usecases.GetPostsByBlogIdUsecase
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
class PostViewModel @Inject constructor(
    private val getAllPostsByBlogIdUsecase: GetPostsByBlogIdUsecase
): ViewModel() {

    private val _uiState: MutableStateFlow<PostState> by lazy {
        MutableStateFlow(PostState())
    }
    val uiState: StateFlow<PostState> = _uiState

    private val _uiError = Channel<String>()
    val uiError = _uiError.receiveAsFlow()

    fun handleEvent(event: PostEvent){
        when(event){
            is PostEvent.GetAllPostsByBlogId -> getAllPostsByBlogId(event.blogId)
        }
    }

    private fun getAllPostsByBlogId(blogId: Int) {
        viewModelScope.launch {
            getAllPostsByBlogIdUsecase.invoke(blogId)
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
                                posts = result.data,
                                isLoading = false
                            )
                        }
                    }
                }
        }

    }
}