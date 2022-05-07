package ru.neexol.rtut.data.lessons.remote

import javax.inject.Inject

class LessonsRemoteDataSource @Inject constructor(
	private val api: LessonsAPI
) {
	suspend fun getGroupLessons(group: String) = api.getGroupLessons(group)
	suspend fun getTeacherLessons(teacher: String) = api.getTeacherLessons(teacher)
	suspend fun getGroupChecksum(group: String) = api.getGroupChecksum(group)
	suspend fun getTimes() = api.getTimes()
}