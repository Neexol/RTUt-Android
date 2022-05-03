package ru.neexol.rtut.data.remote.models

import ru.neexol.rtut.domain.models.LessonTime

@kotlinx.serialization.Serializable
data class LessonTimeResponse(
	val begin: String,
	val end: String
) {
	fun toLessonTime() = LessonTime(begin, end)
}