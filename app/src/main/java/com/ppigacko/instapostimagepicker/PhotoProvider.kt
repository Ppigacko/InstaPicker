package com.ppigacko.instapostimagepicker

import android.provider.MediaStore
import java.io.File

object PhotoProvider {

    private val directoryByPhotos = mutableMapOf<String, MutableList<String>>()

    val allPhotos: List<String>
        get() = directoryByPhotos.values.flatten()

    val directories: List<String>
        get() = directoryByPhotos.keys.toList()

    val directoryPhotos: (String) -> List<String>
        get() = { directoryByPhotos[it] ?: emptyList() }

    fun init(): List<String> {
        val resolver = MainApplication.contentResolver
        val mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val cursor = resolver.query(
            /* uri = */ mImageUri,
            /* projection = */ null,
            /* selection = */ MediaStore.Images.Media.MIME_TYPE + "=? or "
                    + MediaStore.Images.Media.MIME_TYPE + "=? or "
                    + MediaStore.Images.Media.MIME_TYPE + "=?",
            /* selectionArgs = */ arrayOf("image/jpeg", "image/png", "image/x-ms-bmp"),
            /* sortOrder = */ MediaStore.Images.Media.DATE_MODIFIED + " desc"
        ) ?: return emptyList()

        while (cursor.moveToNext()) {

            val path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))

            val file = File(path)
            if (!file.exists()) {
                continue
            }

            // get image parent name
            val parentName = File(path).parentFile?.name ?: continue

            // save all Photo
            if (directoryByPhotos["All Photos"] == null) {
                directoryByPhotos["All Photos"] = mutableListOf()
            }
            directoryByPhotos["All Photos"]?.add(path)

            // save by parent name
            if (directoryByPhotos[parentName] == null) {
                directoryByPhotos[parentName] = mutableListOf()
            }
            directoryByPhotos[parentName]?.add(path)
        }

        cursor.close()

        return directoryByPhotos.values.toList().flatten()
    }
}