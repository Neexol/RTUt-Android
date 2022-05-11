package ru.neexol.rtut.domain.notes.usecases

import kotlinx.coroutines.flow.onCompletion
import ru.neexol.rtut.core.DataFlowUseCase
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.domain.notes.NotesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditAuthorUseCase @Inject constructor(
	private val repository: NotesRepository,
	private val getAuthorUseCase: GetAuthorUseCase
) : DataFlowUseCase<Resource<String>, EditAuthorUseCase.EditAuthorParams>() {
	class EditAuthorParams {
		var author = ""
	}

	override fun performAction(init: EditAuthorParams.() -> Unit) = EditAuthorParams()
		.apply(init)
		.run {
			repository.editAuthor(author).onCompletion {
				getAuthorUseCase.launch()
			}
		}
}