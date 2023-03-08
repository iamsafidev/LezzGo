package com.confiz.lezzgo.di

import com.confiz.lezzgo.repository.EventData
import com.confiz.lezzgo.repository.EventRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindEventRepository(repository: EventData): EventRepository
}