package com.ppigacko.instapostimagepicker.picker.item.media

import com.ppigacko.instapostimagepicker.domain.model.MediaEntity

fun List<MediaEntity>?.toModels(): List<MediaModel> {
    return this?.map { entity ->
        MediaModel(
            id = entity.id.toString(),
            thumbnailImage = entity.imageUri.toString()
        )
    }.orEmpty()
}