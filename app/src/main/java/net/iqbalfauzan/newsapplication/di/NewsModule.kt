package net.iqbalfauzan.newsapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.iqbalfauzan.newsapplication.data.remote.datasource.RemoteNewsDataSource
import net.iqbalfauzan.newsapplication.data.remote.datasource.RemoteNewsDataSourceImpl
import net.iqbalfauzan.newsapplication.data.remote.service.NewsService
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.di
 */
@InstallIn(SingletonComponent::class)
@Module
object NewsModule {
    @Provides
    fun provideNewsDataSource(newsService: NewsService): RemoteNewsDataSource{
        return RemoteNewsDataSourceImpl(newsService)
    }

    @Singleton
    @Provides
    fun provideNewsService(retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }
}