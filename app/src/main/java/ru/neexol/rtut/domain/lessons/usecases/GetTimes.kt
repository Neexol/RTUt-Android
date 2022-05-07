package ru.neexol.rtut.domain.lessons.usecases

import ru.neexol.rtut.data.lessons.local.LessonsLocalDataSource
import javax.inject.Inject

class GetTimes @Inject constructor(
	private val localDataSource: LessonsLocalDataSource
) {
	suspend operator fun invoke() = localDataSource.getTimes()
}