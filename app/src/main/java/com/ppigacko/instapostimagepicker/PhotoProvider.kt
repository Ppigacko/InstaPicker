package com.ppigacko.instapostimagepicker

import android.app.Activity
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

object PhotoProvider {
    fun getImagesPath(activity: Activity): List<String> {
        val listOfAllImages = mutableListOf<String>()
        var pathOfImage: String
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )
        val cursor: Cursor = activity.contentResolver.query(
            uri, projection, null, null, null
        ) ?: return listOf()
        val columnIndexData: Int = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        while (cursor.moveToNext()) {
            pathOfImage = cursor.getString(columnIndexData)
            listOfAllImages.add(pathOfImage)
        }
        cursor.close()
        return listOfAllImages
    }
}