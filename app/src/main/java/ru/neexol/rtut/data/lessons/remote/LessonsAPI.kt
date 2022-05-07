package ru.neexol.rtut.data.lessons.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.neexol.rtut.data.lessons.models.GroupLessons
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.data.lessons.models.LessonTime

interface LessonsAPI {
	@GET("/api/schedule")
	suspend fun getGroupLessons(@Query("group") group: String): GroupLessons

	@GET("/api/schedule")
	suspend fun getTeacherLessons(@Query("teacher") teacher: String): List<Lesson>

	@GET("checksum")
	suspend fun getGroupChecksum(@Query("group") group: String): String

	@GET("times")
	suspend fun getTimes(): List<LessonTime>
}
