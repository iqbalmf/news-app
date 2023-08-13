package net.iqbalfauzan.newsapplication.data.model

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.data.model
 */
data class ArticleResponse(
    val status: String? = "",
    val totalResult: Int? = 0,
    val articles: List<NewsArticleResponse>? = arrayListOf(),
    val code: String? = null,
    val message: String? = null
)

data class NewsArticleResponse(
    val source: Source? = null,
    val author: String? = null,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String
)

data class Source(
    val id: String? = "",
    val name: String? = ""
)