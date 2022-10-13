package com.ppigacko.instapostimagepicker.data.repository

import android.net.Uri
import android.os.Build
import android.provider.MediaStore

object PhotoUtil {
    fun getContentUri(): Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Files.getContentUri("external")
        }
    }
}