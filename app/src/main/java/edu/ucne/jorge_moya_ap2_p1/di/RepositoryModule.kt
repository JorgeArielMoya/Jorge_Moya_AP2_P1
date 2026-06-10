package edu.ucne.jorge_moya_ap2_p1.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.jorge_moya_ap2_p1.data.repository.AmonestacionRepositoryImpl
import edu.ucne.jorge_moya_ap2_p1.domain.repository.AmonestacionRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsRepositoryModule (
        impl: AmonestacionRepositoryImpl
    ) : AmonestacionRepository
}