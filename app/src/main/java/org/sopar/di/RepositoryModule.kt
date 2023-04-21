package org.sopar.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopar.data.repository.AuthRepositoryImpl
import org.sopar.domain.repository.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindAuthRepository(
        loginRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}