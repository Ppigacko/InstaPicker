package com.ppigacko.instapostimagepicker.album

import android.net.Uri

data class AlbumItem(
    val previewImage: Uri,
    val directoryName: String,
    val photoCount: Int,
)