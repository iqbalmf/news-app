package net.iqbalfauzan.newsapplication.data.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import net.iqbalfauzan.newsapplication.data.repository.NewsRepositoryImpl
import net.iqbalfauzan.newsapplication.domain.NewsRepository

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.data.repository.di
 */
@InstallIn(ViewModelComponent::class)
@Module
abstract class NewsDataModule {
    @Binds
    abstract fun provideNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}