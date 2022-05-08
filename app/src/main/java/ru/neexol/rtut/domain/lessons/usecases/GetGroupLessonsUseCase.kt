package ru.neexol.rtut.domain.lessons.usecases

import ru.neexol.rtut.core.FlowUseCase
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.domain.lessons.LessonsRepository
import javax.inject.Inject

class GetGroupLessonsUseCase @Inject constructor(
	private val repository: LessonsRepository
) : FlowUseCase<Resource<List<Lesson>>>() {
	init { launch() }
	override fun performAction() = repository.getGroupLessons()
}