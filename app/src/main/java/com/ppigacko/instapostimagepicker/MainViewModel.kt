package com.ppigacko.instapostimagepicker

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.ppigacko.instapostimagepicker.album.AlbumItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val photoProvider: PhotoProvider
) : ViewModel() {

    private val _albums = MutableStateFlow(photoProvider.directories.map {
        AlbumItem(
            previewImage = PhotoProvider.directoryPhotos(it).first().toUriWithFile(),
            directoryName = it,
            photoCount = PhotoProvider.directoryPhotos(it).size
        )
    })
    val albums: StateFlow<List<AlbumItem>>
        get() = _albums

    private val _photos = MutableStateFlow<List<Uri>>(listOf())
    val photos: StateFlow<List<Uri>>
        get() = _photos

    fun selectAlbum(directoryName: String) {
        _photos.update { photoProvider.directoryPhotos(directoryName).map { it.toUriWithFile() } }
    }
}