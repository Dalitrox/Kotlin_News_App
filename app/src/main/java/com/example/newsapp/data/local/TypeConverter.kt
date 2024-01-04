package com.example.newsapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.newsapp.domain.model.Source

@ProvidedTypeConverter
class TypeConverter {
    @TypeConverter
    fun convertSourceToString(
        source: Source
    ): String {
        return "${source.id},${source.name}"
    }

    @TypeConverter
    fun convertStringToSource(
        source: String
    ): Source {
        return source.split(",").let { array ->
            Source(
                array[0],
                array[1]
            )
        }
    }
}