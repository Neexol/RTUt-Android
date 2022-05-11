package ru.neexol.rtut.data.lessons.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.neexol.rtut.core.Constants.LESSONS_PATH
import ru.neexol.rtut.data.lessons.models.GroupLessons
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.data.lessons.models.LessonTime

interface LessonsApi {
	@GET(LESSONS_PATH)
	suspend fun getGroupLessons(@Query("group") group: String): GroupLessons

	@GET(LESSONS_PATH)
	suspend fun getTeacherLessons(@Query("teacher") teacher: String): List<Lesson>

	@GET("$LESSONS_PATH/checksum")
	suspend fun getGroupChecksum(@Query("group") group: String): String

	@GET("$LESSONS_PATH/times")
	suspend fun getTimes(): List<LessonTime>
}
