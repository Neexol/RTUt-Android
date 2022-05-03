package ru.neexol.rtut.domain.repositories

import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.data.sources.api.LessonsRemoteDataSource
import javax.inject.Inject

class LessonsRepository @Inject constructor(
	private val remoteDataSource: LessonsRemoteDataSource
) {
	suspend fun getGroupLessons() = Resource.from {
		(remoteDataSource.getGroupLessons("ИКБО-12-19") as Resource.Success).data.lessons
	}
}