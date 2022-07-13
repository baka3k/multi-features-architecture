package com.baka3k.core.data.model

import com.baka3k.core.database.model.TopicEntity
import com.baka3k.core.network.model.NetworkTopic

fun NetworkTopic.asEntity() = TopicEntity(
    id = id,
    name = name,
    shortDescription = shortDescription,
    longDescription = longDescription,
    url = url,
    imageUrl = imageUrl
)