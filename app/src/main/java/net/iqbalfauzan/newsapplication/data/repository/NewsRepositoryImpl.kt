package net.iqbalfauzan.newsapplication.data.repository

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.withContext
import net.iqbalfauzan.newsapplication.common.utils.NetworkStatus
import net.iqbalfauzan.newsapplication.data.model.ArticleResponse
import net.iqbalfauzan.newsapplication.data.model.SourceResponse
import net.iqbalfauzan.newsapplication.data.remote.datasource.RemoteNewsDataSource
import net.iqbalfauzan.newsapplication.di.DispatcherProvider
import net.iqbalfauzan.newsapplication.domain.NewsRepository
import javax.inject.Inject

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.data.repository
 */
@ViewModelScoped
class NewsRepositoryImpl @Inject constructor(
    private val remoteNewsDataSource: RemoteNewsDataSource,
    private val dispatcherProvider: DispatcherProvider
) : NewsRepository {
    override suspend fun getArticleBySearch(search: String): NetworkStatus<ArticleResponse> {
        return withContext(dispatcherProvider.io()) {
            val response = remoteNewsDataSource.getArticleBySearch(search)
            if (response is NetworkStatus.Success) {
                if (response.data?.status.equals("ok", true)) {
                    NetworkStatus.Success(response.data)
                } else {
                    NetworkStatus.Error(response.data?.status)
                }
            } else {
                NetworkStatus.Error(response.errorMessage)
            }
        }
    }

    override suspend fun getArticleBySource(source: String): NetworkStatus<ArticleResponse> {
        return withContext(dispatcherProvider.io()) {
            val response = remoteNewsDataSource.getArticleBySource(source)
            if (response is NetworkStatus.Success) {
                if (response.data?.status.equals("ok", true)) {
                    NetworkStatus.Success(response.data)
                } else {
                    NetworkStatus.Error(response.data?.status)
                }
            } else {
                NetworkStatus.Error(response.errorMessage)
            }
        }
    }

    override suspend fun getSourceByCategory(category: String): NetworkStatus<SourceResponse> {
        return withContext(dispatcherProvider.io()) {
            val response = remoteNewsDataSource.getSourceByCategory(category)
            if (response is NetworkStatus.Success) {
                if (response.data?.status.equals("ok", true)) {
                    NetworkStatus.Success(response.data)
                } else {
                    NetworkStatus.Error(response.data?.status)
                }
            } else {
                NetworkStatus.Error(response.errorMessage)
            }
        }
    }

}