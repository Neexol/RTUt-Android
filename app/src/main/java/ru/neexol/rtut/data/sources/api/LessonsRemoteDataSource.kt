package ru.neexol.rtut.data.sources.api

import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.data.LessonsDataSource
import ru.neexol.rtut.domain.models.GroupLessons
import javax.inject.Inject

class LessonsRemoteDataSource @Inject constructor(
	private val api: API
) : LessonsDataSource {
	override suspend fun getGroupLessons(group: String) = Resource.from {
		api.getGroupLessons(group).toGroupLessons()
	}
}