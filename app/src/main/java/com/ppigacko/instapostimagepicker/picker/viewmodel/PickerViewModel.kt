package presentation.picker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppigacko.instapostimagepicker.picker.item.media.toModels
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.picker.usecase.PhotoAlbumUseCase
import domain.picker.usecase.PhotoMediaUseCase
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.ppigacko.instapostimagepicker.picker.item.album.AlbumModel
import com.ppigacko.instapostimagepicker.picker.item.album.toModels
import com.ppigacko.instapostimagepicker.picker.item.media.MediaModel

@HiltViewModel
class PickerViewModel @Inject constructor(
    private val mediaUseCase: PhotoMediaUseCase,
    private val albumUseCase: PhotoAlbumUseCase
) : ViewModel() {

    //    private val _albumList = MutableLiveData<List<AlbumModel>>()
//    val albumList: LiveData<List<AlbumModel>> get() = _albumList
    var albumList = listOf<AlbumModel>()

    private val _currentAlbum = MutableLiveData<AlbumModel>()
    val currentAlbum: LiveData<AlbumModel> get() = _currentAlbum

    private val _currentMedia = MutableLiveData<MediaModel>()
    val currentMedia: LiveData<MediaModel> get() = _currentMedia

    private val _mediaList = MutableLiveData<List<MediaModel>>()
    val mediaList: LiveData<List<MediaModel>> get() = _mediaList

    fun loadAlbum() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                albumUseCase.execute()
            }
            val albumModel = result.toModels()
            albumList = albumModel
            _currentAlbum.value = albumModel.firstOrNull()
            _currentAlbum.value?.let {
                loadMedia(it.bucketId)
            }
        }
    }

    private fun loadMedia(bucketId: String) {
        viewModelScope.launch {
            val mediaList = withContext(Dispatchers.IO) {
                mediaUseCase.execute(bucketId)
            }

            val mediaModels = mediaList.toModels()
            _mediaList.value = mediaModels
            _currentMedia.value = mediaModels.firstOrNull()
            _currentMedia.value?.let {
                updateCurrentMedia(it)
            }
        }
    }

    fun updateCurrentAlbum(album: AlbumModel) {
        _currentAlbum.value = album
        loadMedia(album.bucketId)
    }

    fun updateCurrentMedia(media: MediaModel) {
        _currentMedia.value = media
    }
}