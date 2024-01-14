package com.example.flowsyroomfrancisco.data.sources.remote.di

import android.content.Context
import androidx.room.Room
import com.example.flowsyroomfrancisco.data.MyRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val databaseName = "my_database"

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {


    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(context, MyRoomDatabase::class.java, databaseName).build()

    @Singleton
    @Provides
    fun provideBlogDao(database: MyRoomDatabase) = database.getBlogDao()

    @Singleton
    @Provides
    fun providePostDao(database: MyRoomDatabase)=
        database.getPostDao()

}