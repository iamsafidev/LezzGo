package com.confiz.lezzgo.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import com.confiz.lezzgo.BuildConfig
import com.confiz.lezzgo.data.api.EventApi
import com.confiz.lezzgo.data.network.NetworkHandler
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val DEFAULT_NETWORK_TIMEOUT = 20L

    /* Services */
    @Provides
    @Singleton
    fun provideMoshiConverterFactory(): MoshiConverterFactory {
        val moshi = Moshi.Builder().build()
        return MoshiConverterFactory.create(moshi).asLenient()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .readTimeout(DEFAULT_NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(DEFAULT_NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitClient(
        moshiConverterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_HOST)
            .addConverterFactory(moshiConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideEventService(retrofit: Retrofit): EventApi {
        return retrofit.create(EventApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectionStatus(@ApplicationContext context: Context): NetworkHandler {
        return NetworkHandler(
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.getSystemService(ConnectivityManager::class.java)
            } else {
                context.getSystemService(Application.CONNECTIVITY_SERVICE) as? ConnectivityManager
            }
        )
    }

}