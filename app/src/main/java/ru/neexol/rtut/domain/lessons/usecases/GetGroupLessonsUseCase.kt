package ru.neexol.rtut.domain.lessons.usecases

import ru.neexol.rtut.domain.lessons.LessonsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetGroupLessonsUseCase @Inject constructor(
	private val repository: LessonsRepository
) { operator fun invoke() = repository.getGroupLessons() }