package com.ppigacko.instapostimagepicker.data.repository

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.ppigacko.instapostimagepicker.util.orZero
import com.ppigacko.instapostimagepicker.domain.model.AlbumEntity
import com.ppigacko.instapostimagepicker.domain.repository.AlbumRepository
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    private val context: Context
) : AlbumRepository {
    private val uri = PhotoUtil.getContentUri()
    private val albumMap = LinkedHashMap<Long, AlbumEntity>()

    override fun getAlbum(): List<AlbumEntity> {
        val projection = arrayOf(
            MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Files.FileColumns.BUCKET_ID
        )
        val sortOrder = "${MediaStore.Files.FileColumns.DATE_MODIFIED} DESC"
        val selection =
            "${MediaStore.Files.FileColumns.MEDIA_TYPE}=? OR ${MediaStore.Files.FileColumns.MEDIA_TYPE}=?"
        val selectionArgs = arrayOf(
            MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(),
            MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()
        )

        val cursor = context.contentResolver.query(
            uri,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        cursor?.use {
            while (cursor.moveToNext()) {
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_ID)
                val bucketId = cursor.getLong(idColumn)

                if (albumMap.contains(bucketId)) {
                    continue
                } else {
                    val nameColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME)
                    val bucketName = cursor.getString(nameColumn)
                    val contentUri = ContentUris.withAppendedId(uri, bucketId)
                    albumMap[bucketId] =
                        AlbumEntity(id = bucketId.orZero(), name = bucketName.orEmpty())
                }
            }
        }
        cursor?.close()
        return albumMap.values.toList()
    }
}