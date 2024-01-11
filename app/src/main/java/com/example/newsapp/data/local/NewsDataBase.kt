package com.example.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.domain.model.Article

@Database(entities = [Article::class], version = 2)
@TypeConverters(TypeConverter::class)
abstract class NewsDataBase : RoomDatabase() {
    abstract val newsDao: Dao
}