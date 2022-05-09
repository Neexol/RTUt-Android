package ru.neexol.rtut.domain.notes.usecases

import ru.neexol.rtut.core.FlowUseCase
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.domain.notes.NotesRepository
import javax.inject.Inject

class GetAuthorUseCase @Inject constructor(
	private val repository: NotesRepository
) : FlowUseCase<Resource<String>>() {
	init { launch() }
	override fun performAction() = repository.getAuthor()
}