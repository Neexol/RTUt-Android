package ru.neexol.rtut.data.remote.lessons

import retrofit2.http.GET
import retrofit2.http.Query
import ru.neexol.rtut.data.remote.lessons.models.GroupLessonsResponse
import ru.neexol.rtut.data.remote.lessons.models.LessonResponse
import ru.neexol.rtut.data.remote.lessons.models.LessonTimeResponse

interface LessonsAPI {
	@GET
	suspend fun getGroupLessons(@Query("group") group: String): GroupLessonsResponse

	@GET
	suspend fun getTeacherLessons(@Query("teacher") teacher: String): List<LessonResponse>

	@GET("/checksum")
	suspend fun getGroupChecksum(@Query("group") group: String): String

	@GET("/times")
	suspend fun getTimes(): List<LessonTimeResponse>
}