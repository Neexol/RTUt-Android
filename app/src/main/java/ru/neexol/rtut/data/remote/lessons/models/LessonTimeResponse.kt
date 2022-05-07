package ru.neexol.rtut.data.remote.lessons.models

import ru.neexol.rtut.domain.lessons.models.LessonTime

@kotlinx.serialization.Serializable
data class LessonTimeResponse(
	val begin: String,
	val end: String
) {
	fun toLessonTime() = LessonTime(begin, end)
}