package com.example.test.di

import com.example.test.api.APIFactory
import com.example.test.api.APIManager
import com.example.test.api.APIManagerImpl
import com.example.test.api.Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Providers {

    @Provides
    @Singleton
    fun provideAPI(): Service = APIFactory.getInstance()

    @Provides
    @Singleton
    fun provideAPIManager(api: Service): APIManager = APIManagerImpl(api)
}