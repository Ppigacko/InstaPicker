package com.ppigacko.instapostimagepicker.domain.repository

import com.ppigacko.instapostimagepicker.domain.model.MediaEntity

interface MediaRepository {
    fun getMedia(bucketId: String): List<MediaEntity>
}