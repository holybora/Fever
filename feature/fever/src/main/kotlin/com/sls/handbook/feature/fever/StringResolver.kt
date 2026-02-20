package com.sls.handbook.feature.fever

import android.content.Context
import androidx.annotation.StringRes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Abstraction for resolving Android string resources without a direct Context dependency.
 *
 * Allows ViewModels and mappers to produce localized strings while remaining testable.
 */
interface StringResolver {
    fun getString(@StringRes resId: Int, vararg args: Any): String
}

/** Hilt module providing the production [StringResolver] backed by the application Context. */
@Module
@InstallIn(SingletonComponent::class)
internal object StringResolverModule {
    @Provides
    fun provideStringResolver(@ApplicationContext context: Context): StringResolver =
        object : StringResolver {
            override fun getString(@StringRes resId: Int, vararg args: Any): String =
                context.getString(resId, *args)
        }
}
