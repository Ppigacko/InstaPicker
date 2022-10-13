package com.ppigacko.instapostimagepicker.domain.repository

import com.ppigacko.instapostimagepicker.domain.model.AlbumEntity

interface AlbumRepository {
    fun getAlbum(): List<AlbumEntity>
}