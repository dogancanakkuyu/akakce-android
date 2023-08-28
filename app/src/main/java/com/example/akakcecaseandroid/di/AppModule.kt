package com.example.akakcecaseandroid.di

import com.example.akakcecaseandroid.api.MockApiService
import com.example.akakcecaseandroid.model.impl.ApiRepositoryImpl
import com.example.akakcecaseandroid.model.repo.ApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providesRetrofitInstance(): MockApiService {
        return Retrofit.Builder().baseUrl("https://mocki.io")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MockApiService::class.java)
    }
    @Provides
    fun providesApiRepository(impl: ApiRepositoryImpl) : ApiRepository = impl
}