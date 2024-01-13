package com.example.flowsyroomfrancisco.data.repositories

import com.example.flowsyroomfrancisco.data.BlogDao
import com.example.flowsyroomfrancisco.data.model.toBlogEntity
import com.example.flowsyroomfrancisco.data.sources.remote.RemoteDataSource
import com.example.flowsyroomfrancisco.domain.model.Blog
import com.example.flowsyroomfrancisco.utils.NetworkResultt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BlogRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val blogDao: BlogDao
){
    fun getAllBlogs(): Flow<NetworkResultt<List<Blog>>> {
        return flow {
            emit(NetworkResultt.Loading())
            val result = remoteDataSource.getAllBlogs()
            emit(result)
            if (result is NetworkResultt.Success){
                result.data?.let { lista ->
                    blogDao.deleteBlogList(lista.map {
                        it.toBlogEntity()
                    })
                    blogDao.insertAll(lista.map {
                        it.toBlogEntity()
                    })
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}