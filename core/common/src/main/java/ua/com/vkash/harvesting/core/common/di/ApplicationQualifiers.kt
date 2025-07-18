package com.vkash.harvesting.core.common.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationInfo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DeviceId
