package com.ppigacko.instapostimagepicker.data.repository

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.ppigacko.instapostimagepicker.domain.model.MediaEntity
import com.ppigacko.instapostimagepicker.domain.repository.MediaRepository
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val context: Context
) : MediaRepository {
    private val uri = PhotoUtil.getContentUri()

    override fun getMedia(bucketId: String): List<MediaEntity> {
        val images = mutableListOf<MediaEntity>()
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.MIME_TYPE
        )
        val sortOrder = "${MediaStore.Files.FileColumns.DATE_MODIFIED} DESC"
        val selection = "${MediaStore.Files.FileColumns.BUCKET_ID}=?"
        val selectionArgs = arrayOf(bucketId)
        val cursor = context.contentResolver.query(
            uri,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        cursor?.use {
            while (cursor.moveToNext()) {
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
                val id = cursor.getLong(idColumn)
                val contentUri = ContentUris.withAppendedId(uri, id)
                images.add(
                    MediaEntity(id = id, imageUri = contentUri)
                )
            }
        }
        cursor?.close()
        return images
    }
}