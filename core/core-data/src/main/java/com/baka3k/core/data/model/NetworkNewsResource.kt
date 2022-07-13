package com.baka3k.core.data.model

import com.baka3k.core.database.model.AuthorEntity
import com.baka3k.core.database.model.EpisodeEntity
import com.baka3k.core.database.model.NewsResourceAuthorCrossRef
import com.baka3k.core.database.model.NewsResourceEntity
import com.baka3k.core.database.model.NewsResourceTopicCrossRef
import com.baka3k.core.database.model.TopicEntity
import com.baka3k.core.network.model.NetworkNewsResource
import com.baka3k.core.network.model.NetworkNewsResourceExpanded


fun NetworkNewsResource.asEntity() = NewsResourceEntity(
    id = id,
    episodeId = episodeId,
    title = title,
    content = content,
    url = url,
    headerImageUrl = headerImageUrl,
    publishDate = publishDate,
    type = type,
)

fun NetworkNewsResourceExpanded.asEntity() = NewsResourceEntity(
    id = id,
    episodeId = episodeId,
    title = title,
    content = content,
    url = url,
    headerImageUrl = headerImageUrl,
    publishDate = publishDate,
    type = type,
)

/**
 * A shell [EpisodeEntity] to fulfill the foreign key constraint when inserting
 * a [NewsResourceEntity] into the DB
 */
fun NetworkNewsResource.episodeEntityShell() = EpisodeEntity(
    id = episodeId,
    name = "",
    publishDate = publishDate,
    alternateVideo = null,
    alternateAudio = null,
)

/**
 * A shell [AuthorEntity] to fulfill the foreign key constraint when inserting
 * a [NewsResourceEntity] into the DB
 */
fun NetworkNewsResource.authorEntityShells() =
    authors.map { authorId ->
        AuthorEntity(
            id = authorId,
            name = "",
            imageUrl = "",
            twitter = "",
            mediumPage = "",
            bio = "",
        )
    }

/**
 * A shell [TopicEntity] to fulfill the foreign key constraint when inserting
 * a [NewsResourceEntity] into the DB
 */
fun NetworkNewsResource.topicEntityShells() =
    topics.map { topicId ->
        TopicEntity(
            id = topicId,
            name = "",
            url = "",
            imageUrl = "",
            shortDescription = "",
            longDescription = "",
        )
    }

fun NetworkNewsResource.topicCrossReferences(): List<NewsResourceTopicCrossRef> =
    topics.map { topicId ->
        NewsResourceTopicCrossRef(
            newsResourceId = id,
            topicId = topicId
        )
    }

fun NetworkNewsResource.authorCrossReferences(): List<NewsResourceAuthorCrossRef> =
    authors.map { authorId ->
        NewsResourceAuthorCrossRef(
            newsResourceId = id,
            authorId = authorId
        )
    }
