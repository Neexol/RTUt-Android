package ru.neexol.rtut.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.neexol.rtut.data.remote.models.GroupLessonsResponse
import ru.neexol.rtut.data.remote.models.LessonTimeResponse

interface API {
	@GET("api/schedule")
	suspend fun getGroupLessons(@Query("group") group: String): GroupLessonsResponse

	@GET("api/schedule/checksum")
	suspend fun getGroupChecksum(@Query("group") group: String): String

	@GET("/api/schedule/times")
	suspend fun getTimes(): List<LessonTimeResponse>
}
