package com.ppigacko.instapostimagepicker.data.di

import android.app.Application
import com.ppigacko.instapostimagepicker.data.repository.AlbumRepositoryImpl
import com.ppigacko.instapostimagepicker.data.repository.MediaRepositoryImpl
import com.ppigacko.instapostimagepicker.domain.repository.AlbumRepository
import com.ppigacko.instapostimagepicker.domain.repository.MediaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMediaRepository(context: Application): MediaRepository {
        return MediaRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideAlbumRepository(context: Application): AlbumRepository {
        return AlbumRepositoryImpl(context)
    }
}