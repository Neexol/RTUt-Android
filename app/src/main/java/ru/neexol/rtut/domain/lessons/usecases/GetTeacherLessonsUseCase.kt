package ru.neexol.rtut.domain.lessons.usecases

import ru.neexol.rtut.core.DataFlowUseCase
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.domain.lessons.LessonsRepository
import javax.inject.Inject

class GetTeacherLessonsUseCase @Inject constructor(
	private val repository: LessonsRepository
) : DataFlowUseCase<Resource<List<Lesson>>, GetTeacherLessonsUseCase.GetTeacherLessonsParams>() {
	class GetTeacherLessonsParams {
		var teacher = ""
	}

	override fun performAction(init: GetTeacherLessonsParams.() -> Unit) = GetTeacherLessonsParams()
		.apply(init)
		.run {
			repository.getTeacherLessons(teacher)
		}
}