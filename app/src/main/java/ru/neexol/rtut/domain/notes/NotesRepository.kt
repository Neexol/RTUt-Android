package ru.neexol.rtut.domain.notes

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import ru.neexol.rtut.core.Utils.resourceFlowOf
import ru.neexol.rtut.data.notes.local.NotesLocalDataSource
import ru.neexol.rtut.data.notes.models.NoteType
import ru.neexol.rtut.data.notes.models.PutNote
import ru.neexol.rtut.data.notes.remote.NotesRemoteDataSource
import javax.inject.Inject

class NotesRepository @Inject constructor(
	private val localDataSource: NotesLocalDataSource,
	private val remoteDataSource: NotesRemoteDataSource
) {
	private suspend fun getOrCreateAuthor() = localDataSource.getAuthor()
		?: remoteDataSource.createAuthor().also {
			localDataSource.putAuthor(it)
		}

	fun getAuthor() = resourceFlowOf {
		getOrCreateAuthor()
	}.flowOn(Dispatchers.IO)

	fun editAuthor(authorId: String) = resourceFlowOf {
		remoteDataSource.checkAuthor(authorId).also {
			localDataSource.putAuthor(it)
		}
	}.flowOn(Dispatchers.IO)



	fun getNotes(lessonId: String, week: String) = resourceFlowOf {
		remoteDataSource.getNotes(lessonId, week, getOrCreateAuthor())
	}.flowOn(Dispatchers.IO)

	fun putNote(
		noteId: String?,
		text: String,
		lessonId: String,
		weeks: String,
		authorId: String,
		type: NoteType
	) = resourceFlowOf {
		remoteDataSource.putNote(noteId, PutNote(text, lessonId, weeks, authorId, type))
	}.flowOn(Dispatchers.IO)

	fun deleteNote(noteId: String) = resourceFlowOf {
		remoteDataSource.deleteNote(noteId, getOrCreateAuthor())
	}.flowOn(Dispatchers.IO)
}