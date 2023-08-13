package net.iqbalfauzan.newsapplication.domain

import net.iqbalfauzan.newsapplication.common.utils.NetworkStatus
import net.iqbalfauzan.newsapplication.data.model.ArticleResponse
import net.iqbalfauzan.newsapplication.data.model.SourceResponse

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.domain
 */
interface NewsRepository {
    suspend fun getArticleBySearch(search: String): NetworkStatus<ArticleResponse>
    suspend fun getArticleBySource(source: String): NetworkStatus<ArticleResponse>
    suspend fun getSourceByCategory(category: String): NetworkStatus<SourceResponse>

}