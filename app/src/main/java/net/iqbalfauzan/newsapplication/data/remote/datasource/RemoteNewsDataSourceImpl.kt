package net.iqbalfauzan.newsapplication.data.remote.datasource

import net.iqbalfauzan.newsapplication.common.utils.ApiCall
import net.iqbalfauzan.newsapplication.common.utils.NetworkStatus
import net.iqbalfauzan.newsapplication.data.model.ArticleResponse
import net.iqbalfauzan.newsapplication.data.model.SourceResponse
import net.iqbalfauzan.newsapplication.data.remote.service.NewsService

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.data.remote.datasource
 */
class RemoteNewsDataSourceImpl(private val newsService: NewsService) : RemoteNewsDataSource {
    override suspend fun getArticleBySearch(search: String): NetworkStatus<ArticleResponse> {
        return ApiCall { newsService.getArticleBySearch(search) }
    }

    override suspend fun getArticleBySource(source: String): NetworkStatus<ArticleResponse> {
        return ApiCall { newsService.getArticleBySource(source) }
    }

    override suspend fun getSourceByCategory(category: String): NetworkStatus<SourceResponse> {
        return ApiCall { newsService.getSourceByCategory(category) }
    }

}