package ru.neexol.rtut.domain.repositories

import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.data.local.LessonsLocalDataSource
import ru.neexol.rtut.data.remote.LessonsRemoteDataSource
import javax.inject.Inject

class LessonsRepository @Inject constructor(
	private val localDataSource: LessonsLocalDataSource,
	private val remoteDataSource: LessonsRemoteDataSource
) {
	private companion object {
		const val GROUP = "ИКБО-12-19"
	}

	suspend fun getGroupLessons() = Resource.from {
		(remoteDataSource.getGroupLessons(GROUP) as Resource.Success).data.lessons
	}
}