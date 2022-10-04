package com.ppigacko.instapostimagepicker

import android.net.Uri
import java.io.File

fun String.toUriWithFile(): Uri {
    return Uri.fromFile(File(this))
}