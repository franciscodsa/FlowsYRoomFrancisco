package com.example.flowsyroomfrancisco.ui.list.blogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowsyroomfrancisco.domain.usecases.GetAllBlogsUsecase
import com.example.flowsyroomfrancisco.utils.NetworkResultt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogsViewModel @Inject constructor(
    private val getAllBlogsUsecase: GetAllBlogsUsecase
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
                                message = result.message
                            )
                        }
                        is NetworkResultt.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is NetworkResultt.Success -> _uiState.update {
                            it.copy(
                                blogs = result.data
                            )
                        }
                    }
                }
        }
    }
}