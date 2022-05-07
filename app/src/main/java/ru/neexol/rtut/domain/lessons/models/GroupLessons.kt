package ru.neexol.rtut.domain.lessons.models

@kotlinx.serialization.Serializable
data class GroupLessons(
	val group: String,
	val checksum: String,
	val lessons: List<Lesson>
)