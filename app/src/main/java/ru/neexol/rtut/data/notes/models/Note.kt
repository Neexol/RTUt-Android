package ru.neexol.rtut.data.notes.models

@kotlinx.serialization.Serializable
data class Note(
	val id: String,
	val text: String,
	val lessonId: String,
	val weeks: String,
	val authorId: String,
	val type: NoteType
)