package ru.neexol.rtut.domain.repositories

import ru.neexol.rtut.data.LessonsDataSource
import javax.inject.Inject

class LessonsRepository @Inject constructor(
	private val dataSource: LessonsDataSource
) {
	suspend fun getGroupLessons() = dataSource.getGroupLessons("ИКБО-12-19").lessons
}