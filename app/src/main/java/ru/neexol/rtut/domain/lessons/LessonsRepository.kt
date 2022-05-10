package ru.neexol.rtut.domain.lessons

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.neexol.rtut.core.Utils.emitError
import ru.neexol.rtut.core.Utils.emitLoading
import ru.neexol.rtut.core.Utils.emitSuccess
import ru.neexol.rtut.core.Utils.resourceFlowOf
import ru.neexol.rtut.data.lessons.local.LessonsLocalDataSource
import ru.neexol.rtut.data.lessons.remote.LessonsRemoteDataSource
import javax.inject.Inject

class LessonsRepository @Inject constructor(
	private val localDataSource: LessonsLocalDataSource,
	private val remoteDataSource: LessonsRemoteDataSource
) {
	fun getGroupLessons() = flow {
		emitLoading()
		emitSuccess(localDataSource.getLessons())
		val group = localDataSource.getGroup()
		if (localDataSource.getChecksum() != remoteDataSource.getGroupChecksum(group)) {
			localDataSource.putGroupLessons(remoteDataSource.getGroupLessons(group))
			localDataSource.putTimes(remoteDataSource.getTimes())
			emitSuccess(localDataSource.getLessons())
		}
	}.catch {
		emitError(it)
		emitSuccess(localDataSource.getLessons())
	}.flowOn(Dispatchers.IO)

	fun getTeacherLessons(teacher: String) = resourceFlowOf {
		remoteDataSource.getTeacherLessons(teacher)
	}.flowOn(Dispatchers.IO)
}