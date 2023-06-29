package com.baka3k.data.movie.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baka3k.core.model.Type

@Entity(
    tableName = "type",
)
data class TypeEntity(
    @PrimaryKey val id: Long = 0,
    @ColumnInfo(defaultValue = "") val title: String = "",
)

fun TypeEntity.asExternalModel() = Type(
    id = id,
    title = title,
)