package ru.neexol.rtut.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.neexol.rtut.data.sources.api.API
import ru.neexol.rtut.data.sources.api.LessonsRemoteDataSource
import ru.neexol.rtut.domain.repositories.LessonsRepository
import ru.neexol.rtut.domain.usecases.GetGroupLessons
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LessonsDI {
	@Provides
	@Singleton
	fun provideGetGroupLessons(repository: LessonsRepository) = GetGroupLessons(repository)

	@Provides
	@Singleton
	fun provideLessonsRepository(
		remoteDataSource: LessonsRemoteDataSource
	) = LessonsRepository(remoteDataSource)

	@Provides
	@Singleton
	fun provideLessonsRemoteDataSource(api: API) = LessonsRemoteDataSource(api)
}