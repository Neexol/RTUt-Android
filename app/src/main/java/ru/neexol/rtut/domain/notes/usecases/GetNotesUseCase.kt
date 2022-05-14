package ru.neexol.rtut.domain.notes.usecases

import ru.neexol.rtut.domain.notes.NotesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetNotesUseCase @Inject constructor(
	private val repository: NotesRepository
) { operator fun invoke(lessonId: String, week: String) = repository.getNotes(lessonId, week) }