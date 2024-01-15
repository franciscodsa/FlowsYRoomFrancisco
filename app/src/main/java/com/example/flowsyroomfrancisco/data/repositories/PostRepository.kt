package com.example.flowsyroomfrancisco.data.repositories

import android.util.Log
import com.example.flowsyroomfrancisco.data.PostDao
import com.example.flowsyroomfrancisco.data.model.toPost
import com.example.flowsyroomfrancisco.data.model.toPostEntity
import com.example.flowsyroomfrancisco.data.sources.remote.RemoteDataSource
import com.example.flowsyroomfrancisco.domain.model.Post
import com.example.flowsyroomfrancisco.utils.NetworkResultt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val postDao: PostDao
) {

    fun getAllPosts(): Flow<NetworkResultt<List<Post>>> = flow {
        emit(NetworkResultt.Loading())

        // Consultar la base de datos local
        val localPosts = postDao.getAllPosts()
        if (localPosts.isNotEmpty()) {
            Log.d("PostRepository", "Loading data from local database")
            emit(NetworkResultt.Success(localPosts.map { it.toPost() }))
        } else {
            // Realizar la solicitud al servidor solo si no hay datos locales
            val result = remoteDataSource.getAllPosts()
            emit(result)

            // Actualizar la base de datos local si la solicitud fue exitosa
            if (result is NetworkResultt.Success) {
                result.data?.let { lista ->
                    postDao.deleteAllBlogs()
                    /*postDao.insertAll(lista.map {
                        it.toPostEntity()
                    })*/
                }
                Log.d("PostRepository", "Loading data from server")
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getAllPostsByBlogId(blogId: Int): Flow<NetworkResultt<List<Post>>> = flow {
        emit(NetworkResultt.Loading())

        val result = remoteDataSource.getAllPostsByBlogId(blogId)
        // Consultar la base de datos local por posts con el ID de blog específico
        val localPosts = postDao.getAllPostsByBlogId(blogId)
        if (localPosts.isNotEmpty()) {
            Log.d("PostRepository", "Loading data from local database for Blog ID: $blogId")
            emit(NetworkResultt.Success(localPosts.map { it.toPost() }))
        } else {
            // Realizar la solicitud al servidor solo si no hay datos locales para ese blogId
            val result = remoteDataSource.getAllPostsByBlogId(blogId)
            emit(result)

            // Actualizar la base de datos local si la solicitud fue exitosa
            if (result is NetworkResultt.Success) {
                result.data?.let { lista ->
                    postDao.deleteAllBlogs()

                    postDao.insertAll(lista.map {
                        it.toPostEntity(blogId)
                    })
                }
                Log.d("PostRepository", "Loading data from server for Blog ID: $blogId")
            }
        }
    }.flowOn(Dispatchers.IO)
}