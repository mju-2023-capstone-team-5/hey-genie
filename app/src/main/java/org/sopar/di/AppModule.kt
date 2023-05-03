package org.sopar.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import org.sopar.data.api.KakaoRetrofitApi
import org.sopar.data.api.SoparRetrofitApi
import org.sopar.util.Constants.DATASTORE_NAME
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SoparRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class KakaoRetrofit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Singleton
    @Provides
    @SoparRetrofit
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl("https://mju-2023capstone-team5.run.goorm.site")
            .build()
    }

    @Singleton
    @Provides
    @KakaoRetrofit
    fun provideKakaoRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl("https://dapi.kakao.com/")
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(@SoparRetrofit soparRetrofit: Retrofit): SoparRetrofitApi {
        return soparRetrofit.create(SoparRetrofitApi::class.java)
    }

    @Singleton
    @Provides
    fun provideKakaoApiService(@KakaoRetrofit kakaoRetrofit: Retrofit): KakaoRetrofitApi {
        return kakaoRetrofit.create(KakaoRetrofitApi::class.java)
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> = (
            PreferenceDataStoreFactory.create(
                produceFile = { context.preferencesDataStoreFile(DATASTORE_NAME)}
            )
            )
}