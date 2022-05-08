package ru.neexol.rtut.domain.lessons.usecases

import kotlinx.coroutines.flow.Flow
import ru.neexol.rtut.core.DataFlowUseCase
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.domain.lessons.LessonsRepository
import javax.inject.Inject

class GetTeacherLessonsUseCase @Inject constructor(
	private val repository: LessonsRepository
) : DataFlowUseCase<Resource<List<Lesson>>, GetTeacherLessonsUseCase.Params>() {
	class Params {
		var teacher = ""
	}

	override fun performAction(init: Params.() -> Unit): Flow<Resource<List<Lesson>>> {
		val params = Params().apply(init)
		return repository.getTeacherLessons(params.teacher)
	}
}