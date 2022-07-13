package com.baka3k.core.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.baka3k.core.model.NewsResource

data class PopulatedNewsResource(
    @Embedded
    val entity: NewsResourceEntity,
    @Relation(
        parentColumn = "episode_id",
        entityColumn = "id"
    )
    val episode: EpisodeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = NewsResourceAuthorCrossRef::class,
            parentColumn = "news_resource_id",
            entityColumn = "author_id",
        )
    )
    val authors: List<AuthorEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = NewsResourceTopicCrossRef::class,
            parentColumn = "news_resource_id",
            entityColumn = "topic_id",
        )
    )
    val topics: List<TopicEntity>
)

fun PopulatedNewsResource.asExternalModel() = NewsResource(
    id = entity.id,
    episodeId = entity.episodeId,
    title = entity.title,
    content = entity.content,
    url = entity.url,
    headerImageUrl = entity.headerImageUrl,
    publishDate = entity.publishDate,
    type = entity.type,
    authors = authors.map(AuthorEntity::asExternalModel),
    topics = topics.map(TopicEntity::asExternalModel)
)
