package ru.neexol.rtut.domain.lessons.usecases

import ru.neexol.rtut.domain.lessons.LessonsRepository
import javax.inject.Inject

class GetTeacherLessons @Inject constructor(
	private val repository: LessonsRepository
) {
	suspend operator fun invoke(teacher: String) = repository.getTeacherLessons(teacher)
}