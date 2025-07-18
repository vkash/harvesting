package ua.com.vkash.harvesting.di

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import com.vkash.harvesting.core.common.AppInfo
import com.vkash.harvesting.core.common.di.ApplicationInfo
import com.vkash.harvesting.core.common.di.DeviceId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import ua.com.vkash.harvesting.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Provides
    @Singleton
    @ApplicationInfo
    fun provideApplicationInfo() = AppInfo(
        versionCode = BuildConfig.VERSION_CODE,
        versionName = BuildConfig.VERSION_NAME,
        isDevDevice = BuildConfig.DEBUG &&
            (
                (Build.HARDWARE == "ranchu" && Build.BRAND == "google") ||
                    Build.MODEL == "SM-S931B"
                )
    )

    @Provides
    @DeviceId
    @Singleton
    @SuppressLint("HardwareIds")
    fun provideDeviceId(
        @ApplicationContext context: Context,
        @ApplicationInfo appInfo: AppInfo
    ): String = if (appInfo.isDevDevice) {
        "2133rt35y6b4563v"
    } else {
        android.provider.Settings.Secure.getString(
            context.contentResolver,
            android.provider.Settings.Secure.ANDROID_ID
        ).orEmpty()
    }
}
