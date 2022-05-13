package ru.neexol.rtut.domain.lessons

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.neexol.rtut.core.Utils.emitFailure
import ru.neexol.rtut.core.Utils.emitLoading
import ru.neexol.rtut.core.Utils.emitSuccess
import ru.neexol.rtut.core.Utils.resourceFlowOf
import ru.neexol.rtut.data.lessons.local.LessonsLocalDataSource
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.data.lessons.remote.LessonsRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LessonsRepository @Inject constructor(
	private val localDataSource: LessonsLocalDataSource,
	private val remoteDataSource: LessonsRemoteDataSource
) {
	private fun organizedGroupLessons(lessons: List<Lesson>): List<List<List<Lesson?>>> {
		return lessons.maxOfOrNull { it.weeks.maxOrNull()!! }?.let { maxWeek ->
			List(maxWeek) { week ->
				val weekLessons = lessons.filter { week + 1 in it.weeks }
				List(6) { day ->
					val dayLessons = weekLessons.filter { day == it.day }
					dayLessons.maxOfOrNull { it.number }?.let { maxNumber ->
						MutableList<Lesson?>(maxNumber + 1) { null }.apply {
							dayLessons.forEach { set(it.number, it) }
						}
					} ?: emptyList()
				}
			}
		} ?: emptyList()
	}

	private fun organizedTeacherLessons(lessons: List<Lesson>): List<List<List<Lesson>>> {
		return List(16) { week ->
			val weekLessons = lessons.filter { week + 1 in it.weeks }
			List(6) { day ->
				weekLessons.filter {
					day == it.day
				}.sortedWith(compareBy({ it.number }, { it.name }))
			}
		}
	}

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
		emitFailure(it)
		emitSuccess(localDataSource.getLessons())
	}.map {
		it.map(::organizedGroupLessons)
	}.flowOn(Dispatchers.IO)

	fun getTeacherLessons(teacher: String) = resourceFlowOf {
		remoteDataSource.getTeacherLessons(teacher)
	}.map {
		it.map(::organizedTeacherLessons)
	}.flowOn(Dispatchers.IO)

	fun editGroup(group: String) = resourceFlowOf {
		group.also {
			remoteDataSource.getGroupChecksum(it)
			localDataSource.putGroup(it)
		}
	}.flowOn(Dispatchers.IO)
}