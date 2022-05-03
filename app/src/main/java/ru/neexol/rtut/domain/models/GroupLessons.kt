package ru.neexol.rtut.domain.models

data class GroupLessons(
	val group: String,
	val checksum: String,
	val lessons: List<Lesson>
)