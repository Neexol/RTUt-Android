package ru.neexol.rtut.domain.usecases

import ru.neexol.rtut.domain.repositories.LessonsRepository
import javax.inject.Inject

class GetGroupLessons @Inject constructor(
	private val repository: LessonsRepository
) {
	suspend operator fun invoke() = repository.getGroupLessons()
}