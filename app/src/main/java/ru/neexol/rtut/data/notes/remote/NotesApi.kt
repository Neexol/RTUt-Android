package ru.neexol.rtut.data.notes.remote

import retrofit2.http.*
import ru.neexol.rtut.core.Constants.NOTES_PATH
import ru.neexol.rtut.data.notes.models.Note
import ru.neexol.rtut.data.notes.models.PutNote

interface NotesApi {
	@GET(NOTES_PATH)
	suspend fun getNotes(
		@Query("lessonId") lessonId: String,
		@Query("week") week: String,
		@Query("authorId") authorId: String
	): List<Note>

	@PUT("$NOTES_PATH/{noteId}")
	suspend fun putNote(
		@Path("noteId") noteId: String,
		@Body note: PutNote
	): Note

	@DELETE("$NOTES_PATH/{noteId}")
	suspend fun deleteNote(
		@Path("noteId") noteId: String,
		@Query("authorId") authorId: String
	): String

	@PUT("$NOTES_PATH/authors/{authorId}")
	suspend fun putAuthor(
		@Path("authorId") authorId: String
	): String
}