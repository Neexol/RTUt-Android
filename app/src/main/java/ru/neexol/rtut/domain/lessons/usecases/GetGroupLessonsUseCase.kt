package ru.neexol.rtut.domain.lessons.usecases

import ru.neexol.rtut.core.FlowUseCase
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.domain.lessons.LessonsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetGroupLessonsUseCase @Inject constructor(
	private val repository: LessonsRepository
) : FlowUseCase<Resource<List<List<List<Lesson?>>>>>() {
	override fun performAction() = repository.getGroupLessons()
}