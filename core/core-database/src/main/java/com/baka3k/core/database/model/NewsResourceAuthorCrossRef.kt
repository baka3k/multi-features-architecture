package com.baka3k.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "news_resources_authors",
    primaryKeys = ["news_resource_id", "author_id"],
    foreignKeys = [
        ForeignKey(
            entity = NewsResourceEntity::class,
            parentColumns = ["id"],
            childColumns = ["news_resource_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AuthorEntity::class,
            parentColumns = ["id"],
            childColumns = ["author_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [
        Index(value = ["news_resource_id"]),
        Index(value = ["author_id"]),
    ],
)
data class NewsResourceAuthorCrossRef(
    @ColumnInfo(name = "news_resource_id")
    val newsResourceId: String,
    @ColumnInfo(name = "author_id")
    val authorId: String,
)
