package ru.neexol.rtut.core.di.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.neexol.rtut.data.local.LessonsLocalDataSource
import ru.neexol.rtut.data.remote.LessonsRemoteDataSource
import ru.neexol.rtut.domain.repositories.LessonsRepository
import ru.neexol.rtut.domain.usecases.GetGroupLessons
import ru.neexol.rtut.domain.usecases.GetTeacherLessons
import ru.neexol.rtut.domain.usecases.GetTimes
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LessonsDI {
	@Provides
	@Singleton
	fun provideGetGroupLessons(
		repository: LessonsRepository
	): GetGroupLessons = GetGroupLessons(repository)

	@Provides
	@Singleton
	fun provideGetTeacherLessons(
		repository: LessonsRepository
	): GetTeacherLessons = GetTeacherLessons(repository)

	@Provides
	@Singleton
	fun provideGetTimes(
		localDataSource: LessonsLocalDataSource
	): GetTimes = GetTimes(localDataSource)

	@Provides
	@Singleton
	fun provideRepository(
		localDataSource: LessonsLocalDataSource,
		remoteDataSource: LessonsRemoteDataSource
	): LessonsRepository = LessonsRepository(localDataSource, remoteDataSource)
}