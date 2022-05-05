package ru.neexol.rtut.domain.repositories

import kotlinx.coroutines.flow.flow
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.data.local.LessonsLocalDataSource
import ru.neexol.rtut.data.remote.LessonsRemoteDataSource
import ru.neexol.rtut.domain.models.LessonsWithTimes
import javax.inject.Inject

class LessonsRepository @Inject constructor(
	private val localDataSource: LessonsLocalDataSource,
	private val remoteDataSource: LessonsRemoteDataSource
) {
	suspend fun getGroupLessons() = flow {
		localDataSource.getLessons()?.let {
			emit(Resource.Success(LessonsWithTimes(it, localDataSource.getTimes()!!)))
		}
		try {
			val group = localDataSource.getGroup()
			if (localDataSource.getChecksum() != remoteDataSource.getGroupChecksum(group)) {
				localDataSource.putGroupLessons(remoteDataSource.getGroupLessons(group))
				localDataSource.putTimes(remoteDataSource.getTimes())
			}
		} catch (t: Throwable) {
			emit(Resource.Error(Exception("Не удалось синхронизировать расписание")))
		} finally {
			localDataSource.getLessons()?.let {
				emit(Resource.Success(LessonsWithTimes(it, localDataSource.getTimes()!!)))
			}
		}
	}
}