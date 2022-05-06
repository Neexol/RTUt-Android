package ru.neexol.rtut.domain.usecases

import ru.neexol.rtut.data.local.LessonsLocalDataSource
import javax.inject.Inject

class GetTimes @Inject constructor(
	private val localDataSource: LessonsLocalDataSource
) {
	suspend operator fun invoke() = localDataSource.getTimes()
}