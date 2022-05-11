package ru.neexol.rtut.data.notes.local

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesLocalDataSource @Inject constructor(
	private val manager: NotesPrefsManager
) {
	suspend fun getAuthor() = manager.getAuthor()
	suspend fun putAuthor(author: String) = manager.putAuthor(author)
}