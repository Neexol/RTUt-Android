package ru.neexol.rtut.domain.notes.usecases

import ru.neexol.rtut.core.DataFlowUseCase
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.data.notes.models.Note
import ru.neexol.rtut.data.notes.models.NoteType
import ru.neexol.rtut.domain.notes.NotesRepository
import javax.inject.Inject

class PutNoteUseCase @Inject constructor(
	private val repository: NotesRepository
) : DataFlowUseCase<Resource<Note>, PutNoteUseCase.PutNoteParams>() {
	class PutNoteParams {
		var noteId: String? = null
		var text = ""
		var lessonId = ""
		var weeks = ""
		var authorId = ""
		var type = NoteType.PRIVATE
	}

	override fun performAction(init: PutNoteParams.() -> Unit) = PutNoteParams()
		.apply(init)
		.run {
			repository.putNote(noteId, text, lessonId, weeks, authorId, type)
		}
}