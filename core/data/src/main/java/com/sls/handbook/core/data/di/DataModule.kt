package com.sls.handbook.core.data.di

import com.sls.handbook.core.data.repository.WeatherRepositoryImpl
import com.sls.handbook.core.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** Hilt module that binds repository implementations to their interfaces. */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindWeatherRepository(
        impl: WeatherRepositoryImpl,
    ): WeatherRepository
}
