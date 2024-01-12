package com.example.flowsyroomfrancisco.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flowsyroomfrancisco.data.model.BlogEntity
import com.example.flowsyroomfrancisco.data.model.PostEntity

@Database(entities = [BlogEntity::class, PostEntity::class], version = 1, exportSchema = true)
abstract class MyRoomDatabase : RoomDatabase (){

    abstract fun getBlogDao() : BlogDao
    abstract fun getPostDao() : PostDao

    companion object {
        @Volatile
        private var INSTANCE: MyRoomDatabase? = null

        fun getDatabase(context: Context): MyRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyRoomDatabase::class.java,
                    "item_database"
                )
                    .createFromAsset("database/personas.db")
                    .fallbackToDestructiveMigrationFrom(4)
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}