package ru.neexol.rtut.data.sources.api

import ru.neexol.rtut.data.LessonsDataSource
import javax.inject.Inject

class LessonsAPIDataSource @Inject constructor(
	private val api: API
) : LessonsDataSource {
	override suspend fun getGroupLessons(group: String) = api.getGroupLessons(group).toGroupLessons()
}