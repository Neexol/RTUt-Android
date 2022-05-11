package ru.neexol.rtut.domain.notes.usecases

import ru.neexol.rtut.core.DataFlowUseCase
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.data.notes.models.Note
import ru.neexol.rtut.domain.notes.NotesRepository
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
	private val repository: NotesRepository
) : DataFlowUseCase<Resource<List<Note>>, GetNotesUseCase.GetNotesParams>() {
	class GetNotesParams {
		var lessonId = ""
		var week = ""
	}

	override fun performAction(init: GetNotesParams.() -> Unit) = GetNotesParams()
		.apply(init)
		.run { repository.getNotes(lessonId, week) }
}