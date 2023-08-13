package net.iqbalfauzan.newsapplication.data.remote.service

import net.iqbalfauzan.newsapplication.data.model.ArticleResponse
import net.iqbalfauzan.newsapplication.data.model.SourceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.data.remote.service
 */
interface NewsService {
    @GET("/v2/everything")
    suspend fun getArticleBySearch(@Query("q") search: String): Response<ArticleResponse>

    @GET("/v2/everything")
    suspend fun getArticleBySource(@Query("sources") source: String): Response<ArticleResponse>

    @GET("/v2/sources")
    suspend fun getSourceByCategory(@Query("category") category: String): Response<SourceResponse>
}