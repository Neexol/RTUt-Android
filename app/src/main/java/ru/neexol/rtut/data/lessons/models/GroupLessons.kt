package ru.neexol.rtut.data.lessons.models

@kotlinx.serialization.Serializable
data class GroupLessons(
	val group: String,
	val checksum: String,
	val lessons: List<Lesson>
)