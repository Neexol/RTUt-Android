package ru.neexol.rtut.data.remote.models

import ru.neexol.rtut.domain.models.GroupLessons

@kotlinx.serialization.Serializable
data class GroupLessonsResponse(
	val group: String,
	val checksum: String,
	val lessons: List<LessonResponse>
) {
	fun toGroupLessons() = GroupLessons(group, checksum, lessons.map { it.toLesson() })
}