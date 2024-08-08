package com.example.newsshorts.di

import com.example.newsshorts.data.api.NewsApi
import com.example.newsshorts.data.datasource.NewsDataSource
import com.example.newsshorts.data.datasource.NewsNetworkDataSource
import com.example.newsshorts.data.repository.NewsRepository
import com.example.newsshorts.util.Constants
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
    fun providesNewsApiService(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

    @Singleton
    @Provides
    fun provideNetworkDataSource(api: NewsApi): NewsDataSource = NewsNetworkDataSource(api)

    @Singleton
    @Provides
    fun provideNewsRepository(newsNetworkDataSource: NewsNetworkDataSource): NewsRepository =
        NewsRepository(newsNetworkDataSource)
}