package ru.neexol.rtut.data.remote

import ru.neexol.rtut.core.Resource
import javax.inject.Inject

class LessonsRemoteDataSource @Inject constructor(
	private val api: API
) {
	suspend fun getGroupLessons(group: String) = Resource.from {
		api.getGroupLessons(group).toGroupLessons()
	}

	suspend fun getGroupChecksum(group: String) = Resource.from {
		api.getGroupChecksum(group)
	}

	suspend fun getTimes() = Resource.from {
		api.getTimes()
	}
}