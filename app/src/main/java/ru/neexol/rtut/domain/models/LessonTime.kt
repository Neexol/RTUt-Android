package ru.neexol.rtut.domain.models

@kotlinx.serialization.Serializable
data class LessonTime(
	val begin: String,
	val end: String
)