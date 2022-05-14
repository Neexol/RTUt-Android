package ru.neexol.rtut.domain.notes.usecases

import ru.neexol.rtut.data.notes.models.NoteType
import ru.neexol.rtut.domain.notes.NotesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PutNoteUseCase @Inject constructor(
	private val repository: NotesRepository
) {
	operator fun invoke(
		noteId: String? = null,
		text: String,
		lessonId: String,
		weeks: String,
		authorId: String,
		type: NoteType
	) = repository.putNote(noteId, text, lessonId, weeks, authorId, type)
}