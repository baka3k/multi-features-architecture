package com.baka3k.core.data.model

import com.baka3k.core.database.model.AuthorEntity
import com.baka3k.core.network.model.NetworkAuthor


fun NetworkAuthor.asEntity() = AuthorEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    twitter = twitter,
    mediumPage = mediumPage,
    bio = bio,
)
