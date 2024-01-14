package com.example.flowsyroomfrancisco.data.repositories

import android.util.Log
import com.example.flowsyroomfrancisco.data.BlogDao
import com.example.flowsyroomfrancisco.data.model.toBlog
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
    fun getAllBlogs(): Flow<NetworkResultt<List<Blog>>> = flow {
        emit(NetworkResultt.Loading())

        //Consultar la base de datos local
        val localBlogs = blogDao.getAllBlogs()
        if (localBlogs.isNotEmpty()) {
            Log.d("Repository", "Loading data from local database")
            emit(NetworkResultt.Success(localBlogs.map { it.toBlog() }))
        } else {
            //Realizar la solicitud al servidor solo si no hay datos locales
            val result = remoteDataSource.getAllBlogs()
            emit(result)

            //Actualizar la base de datos local si la solicitud fue exitosa
            if (result is NetworkResultt.Success) {
                result.data?.let { lista ->
                    blogDao.deleteBlogList(lista.map {
                        it.toBlogEntity()
                    })
                    blogDao.insertAll(lista.map {
                        it.toBlogEntity()
                    })
                }
                Log.d("Repository", "Loading data from server")
            }
        }
    }.flowOn(Dispatchers.IO)
}