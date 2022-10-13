package domain.picker.usecase

import com.ppigacko.instapostimagepicker.domain.model.MediaEntity
import com.ppigacko.instapostimagepicker.domain.repository.MediaRepository
import javax.inject.Inject

class PhotoMediaUseCase @Inject constructor(
    private val repository: MediaRepository
) {

    suspend fun execute(bucketId: String): List<MediaEntity> = repository.getMedia(bucketId)

}