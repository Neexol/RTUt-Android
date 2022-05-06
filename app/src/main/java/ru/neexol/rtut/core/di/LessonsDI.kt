package ru.neexol.rtut.core.di

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
	fun provideGetTeacherLessons(repository: LessonsRepository) = GetTeacherLessons(repository)

	@Provides
	@Singleton
	fun provideGetGroupLessons(repository: LessonsRepository) = GetGroupLessons(repository)

	@Provides
	@Singleton
	fun provideGetTimes(localDataSource: LessonsLocalDataSource) = GetTimes(localDataSource)

	@Provides
	@Singleton
	fun provideLessonsRepository(
		localDataSource: LessonsLocalDataSource,
		remoteDataSource: LessonsRemoteDataSource
	) = LessonsRepository(localDataSource, remoteDataSource)
}