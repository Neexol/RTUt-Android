package ru.neexol.rtut.data.remote

import javax.inject.Inject

class LessonsRemoteDataSource @Inject constructor(
	private val api: API
) {
	suspend fun getGroupLessons(group: String) = api.getGroupLessons(group).toGroupLessons()
	suspend fun getGroupChecksum(group: String) = api.getGroupChecksum(group)
	suspend fun getTimes() = api.getTimes().map { it.toLessonTime() }
}