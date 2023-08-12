package net.iqbalfauzan.newsapplication.data.model

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.data.model
 */
data class SourceResponse(
    val status: String? = "",
    val sources: List<NewsSourceResponse>? = arrayListOf()
)

data class NewsSourceResponse(
    val id: String? = "",
    val name: String? = "",
    val description: String? = "",
    val url: String? = "",
    val category: String? = "",
    val language: String? = "",
    val country: String? = "",
)