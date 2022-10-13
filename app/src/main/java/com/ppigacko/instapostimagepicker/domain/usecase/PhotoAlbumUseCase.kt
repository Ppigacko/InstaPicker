package domain.picker.usecase

import com.ppigacko.instapostimagepicker.domain.model.AlbumEntity
import com.ppigacko.instapostimagepicker.domain.repository.AlbumRepository
import javax.inject.Inject

class PhotoAlbumUseCase @Inject constructor(
    private val repository: AlbumRepository
) {

    fun execute(): List<AlbumEntity> = repository.getAlbum()

}