package net.iqbalfauzan.newsapplication.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.iqbalfauzan.newsapplication.BuildConfig
import net.iqbalfauzan.newsapplication.common.utils.NoConnectionInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.di
 */
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ApiUrl

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ApiKeys

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NoConnectionInterceptor

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class InternetInterceptor

    @Provides
    @ApiUrl
    @Singleton
    fun providesApiUrl(): String = BuildConfig.BASE_URL

    @Provides
    @ApiKeys
    @Singleton
    fun providesApiKey(): String = BuildConfig.APIKEY

    @Singleton
    @Provides
    @NoConnectionInterceptor
    internal fun provideNoConnectionInterceptor(
        @ApplicationContext mContext: Context
    ): Interceptor {
        return NoConnectionInterceptor(mContext)
    }

    @Singleton
    @Provides
    @InternetInterceptor
    internal fun providesInternetInterceptor(
        @ApiKeys apiKey: String
    ): Interceptor {
        return Interceptor {
            val request = it.request().newBuilder().addHeader("x-api-key", apiKey).build()
            val response = it.proceed(request)
            response
        }
    }

    @Singleton
    @Provides
    internal fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return httpLoggingInterceptor
    }

    @Provides
    internal fun provideOkhttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @NoConnectionInterceptor noConnectionInterceptor: Interceptor,
        @InternetInterceptor internetInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(noConnectionInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(internetInterceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    internal fun provideServiceRetrofit(@ApiUrl url: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}