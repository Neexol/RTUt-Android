package ru.neexol.rtut.domain.usecases

import ru.neexol.rtut.domain.repositories.LessonsRepository
import javax.inject.Inject

class GetTeacherLessons @Inject constructor(
	private val repository: LessonsRepository
) {
	suspend operator fun invoke(teacher: String) = repository.getTeacherLessons(teacher)
}