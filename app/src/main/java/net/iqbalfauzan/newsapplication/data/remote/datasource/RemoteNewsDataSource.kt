package net.iqbalfauzan.newsapplication.data.remote.datasource

import net.iqbalfauzan.newsapplication.common.utils.NetworkStatus
import net.iqbalfauzan.newsapplication.data.model.ArticleResponse
import net.iqbalfauzan.newsapplication.data.model.SourceResponse

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.data.remote.datasource
 */
interface RemoteNewsDataSource {
    suspend fun getArticleBySearch(search: String): NetworkStatus<ArticleResponse>
    suspend fun getArticleBySource(source: String): NetworkStatus<ArticleResponse>
    suspend fun getSourceByCategory(category: String): NetworkStatus<SourceResponse>

}