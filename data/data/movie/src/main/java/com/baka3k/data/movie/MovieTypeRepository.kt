package com.baka3k.data.movie

import com.baka3k.core.common.data.Syncable

interface MovieTypeRepository: Syncable {
    suspend fun initDefaultType()
}