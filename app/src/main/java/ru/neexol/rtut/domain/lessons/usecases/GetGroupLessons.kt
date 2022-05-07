package ru.neexol.rtut.domain.lessons.usecases

import ru.neexol.rtut.domain.lessons.LessonsRepository
import javax.inject.Inject

class GetGroupLessons @Inject constructor(
	private val repository: LessonsRepository
) {
	suspend operator fun invoke() = repository.getGroupLessons()
}