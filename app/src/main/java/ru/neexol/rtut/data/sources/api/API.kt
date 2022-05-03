package ru.neexol.rtut.data.sources.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.neexol.rtut.data.sources.api.models.responses.GroupLessonsResponse

interface API {
	@GET("api/schedule")
	suspend fun getGroupLessons(@Query("group") group: String): GroupLessonsResponse
}
