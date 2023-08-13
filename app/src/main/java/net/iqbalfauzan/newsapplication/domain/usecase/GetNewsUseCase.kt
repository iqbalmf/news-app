package net.iqbalfauzan.newsapplication.domain.usecase

import net.iqbalfauzan.newsapplication.common.utils.NetworkStatus
import net.iqbalfauzan.newsapplication.data.model.ArticleResponse
import net.iqbalfauzan.newsapplication.data.model.SourceResponse
import net.iqbalfauzan.newsapplication.domain.NewsRepository
import javax.inject.Inject

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.domain.usecase
 */
class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend fun getSourceByCategory(category: String): NetworkStatus<SourceResponse> {
        return newsRepository.getSourceByCategory(category)
    }

    suspend fun getArticleBySource(source: String): NetworkStatus<ArticleResponse> {
        return newsRepository.getArticleBySource(source)
    }

    suspend fun getArticleBySearch(search: String): NetworkStatus<ArticleResponse> {
        return newsRepository.getArticleBySearch(search)
    }
}