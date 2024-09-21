package com.example.newsshorts.core.di

import com.example.newsshorts.core.data.network.NewsApiService
import com.example.newsshorts.core.domain.NewsDataSource
import com.example.newsshorts.core.data.network.NewsNetworkDataSource
import com.example.newsshorts.core.data.repository.NewsRepositoryImpl
import com.example.newsshorts.core.domain.NewsRepository
import com.example.newsshorts.core.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRetrofitService(): Retrofit {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesNewsApiService(retrofit: Retrofit): NewsApiService = retrofit.create(NewsApiService::class.java)

    @Singleton
    @Provides
    fun provideNetworkDataSource(api: NewsApiService): NewsDataSource = NewsNetworkDataSource(api)

    @Singleton
    @Provides
    fun provideNewsRepository(newsNetworkDataSource: NewsNetworkDataSource): NewsRepository =
        NewsRepositoryImpl(newsNetworkDataSource)
}