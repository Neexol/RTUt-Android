package ru.neexol.rtut.domain.notes.usecases

import ru.neexol.rtut.core.DataFlowUseCase
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.domain.notes.NotesRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
	private val repository: NotesRepository
) : DataFlowUseCase<Resource<String>, DeleteNoteUseCase.DeleteNoteParams>() {
	class DeleteNoteParams {
		var noteId = ""
	}

	override fun performAction(init: DeleteNoteParams.() -> Unit) = DeleteNoteParams()
		.apply(init)
		.run {
			repository.deleteNote(noteId)
		}
}