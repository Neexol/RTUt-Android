package ru.neexol.rtut.data.notes.remote

import ru.neexol.rtut.data.notes.models.PutNote
import javax.inject.Inject

class NotesRemoteDataSource @Inject constructor(
	private val api: NotesAPI
) {
	suspend fun getNotes(lessonId: String, week: String, authorId: String) = api.getNotes(lessonId, week, authorId)
	suspend fun putNote(noteId: String?, note: PutNote) = api.putNote(noteId ?: "", note)
	suspend fun deleteNote(noteId: String, authorId: String) = api.deleteNote(noteId, authorId)

	suspend fun createAuthor() = api.putAuthor("")
	suspend fun checkAuthor(authorId: String) = api.putAuthor(authorId)
}