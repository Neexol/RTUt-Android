package ru.neexol.rtut.core.di.data.notes

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.neexol.rtut.core.Constants
import ru.neexol.rtut.core.di.annotations.NotesDataStore
import ru.neexol.rtut.data.notes.local.NotesLocalDataSource
import ru.neexol.rtut.data.notes.local.NotesPrefsManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotesLocalDI {
	@Provides
	@Singleton
	fun provideDataSource(
		manager: NotesPrefsManager
	): NotesLocalDataSource = NotesLocalDataSource(manager)

	@Provides
	@Singleton
	fun providePrefsManager(
		@NotesDataStore dataStore: DataStore<Preferences>
	): NotesPrefsManager = NotesPrefsManager(dataStore)

	@Provides
	@Singleton
	@NotesDataStore
	fun provideDataStore(
		@ApplicationContext context: Context
	): DataStore<Preferences> = PreferenceDataStoreFactory.create(
		produceFile = { context.preferencesDataStoreFile(Constants.NOTES_PREFS_NAME) }
	)
}