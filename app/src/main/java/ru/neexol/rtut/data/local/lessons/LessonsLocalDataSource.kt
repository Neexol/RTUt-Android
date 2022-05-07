package ru.neexol.rtut.data.local.lessons

import ru.neexol.rtut.domain.models.GroupLessons
import ru.neexol.rtut.domain.models.LessonTime
import javax.inject.Inject

class LessonsLocalDataSource @Inject constructor(
	private val manager: LessonsPrefsManager
) {
	suspend fun getGroup() = manager.getGroup()
	suspend fun getChecksum() = manager.getChecksum()
	suspend fun getLessons() = manager.getLessons()
	suspend fun getTimes() = manager.getTimes()

	suspend fun putGroupLessons(groupLessons: GroupLessons) = manager.putGroupLessons(groupLessons)
	suspend fun putTimes(times: List<LessonTime>) = manager.putTimes(times)
}