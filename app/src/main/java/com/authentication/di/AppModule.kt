package com.authentication.di

import android.content.Context
import android.content.SharedPreferences
import com.authentication.model.remote.ApiService
import com.authentication.model.remote.BASE_URL
import com.authentication.model.repository.TokenContainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                val oldRequest = it.request()
                val newRequestBuilder = oldRequest.newBuilder()
                if (TokenContainer.token != null)
                    newRequestBuilder.addHeader("Authorization", "Bearer ${TokenContainer.token}")
                newRequestBuilder.method(oldRequest.method(), oldRequest.body())
                return@addInterceptor it.proceed(newRequestBuilder.build())
            }
            .build()
    }

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            "app_info", Context.MODE_PRIVATE
        )

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideCurrencyService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

}