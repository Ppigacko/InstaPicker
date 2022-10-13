package com.ppigacko.instapostimagepicker.picker.item.album

import com.ppigacko.instapostimagepicker.domain.model.AlbumEntity

fun List<AlbumEntity>?.toModels(): List<AlbumModel> {
    return this?.map { album ->
        AlbumModel(
            bucketId = album.id.toString(),
            bucketName = album.name
        )
    }.orEmpty()
}