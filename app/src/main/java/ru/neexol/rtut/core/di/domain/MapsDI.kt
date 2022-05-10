package ru.neexol.rtut.core.di.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.neexol.rtut.data.maps.local.MapsLocalDataSource
import ru.neexol.rtut.data.maps.remote.MapsRemoteDataSource
import ru.neexol.rtut.domain.maps.MapsRepository
import ru.neexol.rtut.domain.maps.usecases.GetMapsUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapsDI {
	@Provides
	@Singleton
	fun provideGetMapsUseCase(
		repository: MapsRepository
	): GetMapsUseCase = GetMapsUseCase(repository)

	@Provides
	@Singleton
	fun provideRepository(
		localDataSource: MapsLocalDataSource,
		remoteDataSource: MapsRemoteDataSource
	): MapsRepository = MapsRepository(localDataSource, remoteDataSource)
}