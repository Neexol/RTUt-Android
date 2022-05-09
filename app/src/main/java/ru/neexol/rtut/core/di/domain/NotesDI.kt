package ru.neexol.rtut.core.di.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.neexol.rtut.data.notes.local.NotesLocalDataSource
import ru.neexol.rtut.data.notes.remote.NotesRemoteDataSource
import ru.neexol.rtut.domain.notes.NotesRepository
import ru.neexol.rtut.domain.notes.usecases.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotesDI {
	@Provides
	@Singleton
	fun provideGetAuthorUseCase(
		repository: NotesRepository
	): GetAuthorUseCase = GetAuthorUseCase(repository)

	@Provides
	@Singleton
	fun provideEditAuthorUseCase(
		repository: NotesRepository
	): EditAuthorUseCase = EditAuthorUseCase(repository)

	@Provides
	@Singleton
	fun provideGetNotesUseCase(
		repository: NotesRepository
	): GetNotesUseCase = GetNotesUseCase(repository)

	@Provides
	@Singleton
	fun providePutNoteUseCase(
		repository: NotesRepository
	): PutNoteUseCase = PutNoteUseCase(repository)

	@Provides
	@Singleton
	fun provideDeleteNoteUseCase(
		repository: NotesRepository
	): DeleteNoteUseCase = DeleteNoteUseCase(repository)

	@Provides
	@Singleton
	fun provideRepository(
		localDataSource: NotesLocalDataSource,
		remoteDataSource: NotesRemoteDataSource
	): NotesRepository = NotesRepository(localDataSource, remoteDataSource)
}