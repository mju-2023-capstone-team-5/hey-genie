package org.sopar.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopar.data.repository.AuthRepositoryImpl
import org.sopar.data.repository.KakaoRepositoryImpl
import org.sopar.data.repository.MapRepositoryImp
import org.sopar.domain.repository.AuthRepository
import org.sopar.domain.repository.KakaoRepository
import org.sopar.domain.repository.MapRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindAuthRepository(
        loginRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Singleton
    @Binds
    abstract fun bindKakaoRepository(
        kakaoRepositoryImpl: KakaoRepositoryImpl
    ): KakaoRepository

    @Singleton
    @Binds
    abstract fun bindMapRepository(
        mapRepositoryImpl: MapRepositoryImp
    ): MapRepository
}