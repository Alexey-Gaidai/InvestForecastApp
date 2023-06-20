package com.example.investforecast.di

import com.example.investforecast.data.InvestRepositoryImpl
import com.example.investforecast.data.nw.InvestAPIService
import com.example.investforecast.data.nw.NewsAPIService
import com.example.investforecast.domain.InvestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideInvestApi(): InvestAPIService {
        return InvestAPIService.createApiService()
    }

    @Provides
    @Singleton
    fun provideNewsApi(): NewsAPIService {
        return NewsAPIService.createApiService()
    }

    @Provides
    @Singleton
    fun provideInvestRepository(investApi: InvestAPIService, newApi:NewsAPIService): InvestRepository {
        return InvestRepositoryImpl(investApi, newApi)
    }

}