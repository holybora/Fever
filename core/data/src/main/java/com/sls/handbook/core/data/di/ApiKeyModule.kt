package com.sls.handbook.core.data.di

import com.sls.handbook.core.network.ApiKeyProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object ApiKeyModule {

    @Provides
    fun provideApiKeyProvider(): ApiKeyProvider = object : ApiKeyProvider {
        override fun getApiKey(): String = "ae103060692fe13422deb98285505dc6"
    }
}
