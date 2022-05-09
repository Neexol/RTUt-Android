package ru.neexol.rtut.domain.notes.usecases

import ru.neexol.rtut.core.DataFlowUseCase
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.domain.notes.NotesRepository
import javax.inject.Inject

class EditAuthorUseCase @Inject constructor(
	private val repository: NotesRepository
) : DataFlowUseCase<Resource<String>, EditAuthorUseCase.EditAuthorParams>() {
	class EditAuthorParams {
		var authorId = ""
	}

	override fun performAction(init: EditAuthorParams.() -> Unit) = EditAuthorParams()
		.apply(init)
		.run {
			repository.editAuthor(authorId)
		}
}