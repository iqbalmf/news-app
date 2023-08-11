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
import javax.inject.Singleton

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.di
 */
@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    @Singleton
    @Provides
    internal fun provideNoConnectionInterceptor(
        @ApplicationContext mContext: Context
    ): Interceptor {
        return NoConnectionInterceptor(mContext)
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
        noConnectionInterceptor: Interceptor,
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader(
                    "Connection",
                    "close"
                ).build()
                chain.proceed(request)
            }
            .addInterceptor(noConnectionInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

        return okHttpClient
    }

    @Provides
    internal fun provideServiceRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}