package ru.neexol.rtut.core.di.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.neexol.rtut.data.lessons.local.LessonsLocalDataSource
import ru.neexol.rtut.data.lessons.remote.LessonsRemoteDataSource
import ru.neexol.rtut.domain.lessons.LessonsRepository
import ru.neexol.rtut.domain.lessons.usecases.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LessonsDI {
	@Provides
	@Singleton
	fun provideGetGroupLessonsUseCase(
		repository: LessonsRepository
	): GetGroupLessonsUseCase = GetGroupLessonsUseCase(repository)

	@Provides
	@Singleton
	fun provideGetTeacherLessonsUseCase(
		repository: LessonsRepository
	): GetTeacherLessonsUseCase = GetTeacherLessonsUseCase(repository)

	@Provides
	@Singleton
	fun provideGetGroupUseCase(
		dataSource: LessonsLocalDataSource
	): GetGroupUseCase = GetGroupUseCase(dataSource)

	@Provides
	@Singleton
	fun provideEditGroupUseCase(
		repository: LessonsRepository,
		getGroupUseCase: GetGroupUseCase,
		getGroupLessonsUseCase: GetGroupLessonsUseCase
	): EditGroupUseCase = EditGroupUseCase(repository, getGroupUseCase, getGroupLessonsUseCase)

	@Provides
	@Singleton
	fun provideGetTimesUseCase(
		dataSource: LessonsLocalDataSource
	): GetTimesUseCase = GetTimesUseCase(dataSource)

	@Provides
	@Singleton
	fun provideRepository(
		localDataSource: LessonsLocalDataSource,
		remoteDataSource: LessonsRemoteDataSource
	): LessonsRepository = LessonsRepository(localDataSource, remoteDataSource)
}