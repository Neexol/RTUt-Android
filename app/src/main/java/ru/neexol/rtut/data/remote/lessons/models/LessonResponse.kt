package ru.neexol.rtut.data.remote.lessons.models

import ru.neexol.rtut.domain.models.Lesson

@kotlinx.serialization.Serializable
data class LessonResponse(
	val id: String,
	val name: String,
	val type: String,
	val teacher: String,
	val classroom: String,
	val day: Int,
	val number: Int,
	val weeks: List<Int>
) {
	fun toLesson() = Lesson(id, name, type, teacher, classroom, day, number, weeks)
}