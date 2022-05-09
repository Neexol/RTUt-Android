package ru.neexol.rtut.data.notes.remote

import retrofit2.http.*
import ru.neexol.rtut.data.notes.models.Note
import ru.neexol.rtut.data.notes.models.PutNote

interface NotesAPI {
	@GET("/api/notes")
	suspend fun getNotes(
		@Query("lessonId") lessonId: String,
		@Query("week") week: String,
		@Query("authorId") authorId: String
	): List<Note>

	@PUT("/api/notes/{noteId}")
	suspend fun putNote(
		@Path("noteId") noteId: String,
		@Body note: PutNote
	): Note

	@DELETE("/api/notes/{noteId}")
	suspend fun deleteNote(
		@Path("noteId") noteId: String,
		@Query("authorId") authorId: String
	): String

	@PUT("/api/notes/authors/{authorId}")
	suspend fun putAuthor(
		@Path("authorId") authorId: String
	): String
}