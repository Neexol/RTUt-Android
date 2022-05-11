package ru.neexol.rtut.data.notes.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import ru.neexol.rtut.core.Utils.get
import ru.neexol.rtut.core.Utils.put
import ru.neexol.rtut.core.di.annotations.NotesDataStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesPrefsManager @Inject constructor(
	@NotesDataStore private val dataStore: DataStore<Preferences>
) {
	private companion object {
		val AUTHOR = stringPreferencesKey("author")
	}

	suspend fun getAuthor() = dataStore.get(AUTHOR)
	suspend fun putAuthor(author: String) = dataStore.put(AUTHOR, author)
}