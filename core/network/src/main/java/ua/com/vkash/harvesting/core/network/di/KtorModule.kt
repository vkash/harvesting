package ua.com.vkash.harvesting.core.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import ua.com.vkash.harvesting.core.network.ApiClient
import ua.com.vkash.harvesting.core.network.ApiClientImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class KtorModule {
    @Binds
    @Singleton
    abstract fun provideApiClient(impl: ApiClientImpl): ApiClient
}
