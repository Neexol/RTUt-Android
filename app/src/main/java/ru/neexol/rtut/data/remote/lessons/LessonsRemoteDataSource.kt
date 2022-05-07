package ru.neexol.rtut.data.remote.lessons

import javax.inject.Inject

class LessonsRemoteDataSource @Inject constructor(
	private val api: LessonsAPI
) {
	suspend fun getGroupLessons(group: String) = api.getGroupLessons(group).toGroupLessons()
	suspend fun getTeacherLessons(teacher: String) = api.getTeacherLessons(teacher).map { it.toLesson() }
	suspend fun getGroupChecksum(group: String) = api.getGroupChecksum(group)
	suspend fun getTimes() = api.getTimes().map { it.toLessonTime() }
}