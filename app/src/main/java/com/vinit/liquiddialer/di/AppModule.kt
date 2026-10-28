package com.vinit.liquiddialer.di

import android.content.Context
import com.vinit.liquiddialer.data.repository.CallLogRepository
import com.vinit.liquiddialer.data.repository.ContactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContactRepository(
        @ApplicationContext context: Context
    ): ContactRepository = ContactRepository(context)

    @Provides
    @Singleton
    fun provideCallLogRepository(
        @ApplicationContext context: Context
    ): CallLogRepository = CallLogRepository(context)
}
