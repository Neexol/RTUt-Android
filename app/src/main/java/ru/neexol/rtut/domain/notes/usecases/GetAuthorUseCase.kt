package ru.neexol.rtut.domain.notes.usecases

import ru.neexol.rtut.domain.notes.NotesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAuthorUseCase @Inject constructor(
	private val repository: NotesRepository
) { operator fun invoke() = repository.getAuthor() }