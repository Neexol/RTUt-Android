package ru.neexol.rtut.core.di

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
import ru.neexol.rtut.core.di.annotations.LessonsDataStore
import ru.neexol.rtut.core.di.annotations.MapsDataStore
import ru.neexol.rtut.core.di.annotations.NotesDataStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
	@Provides
	@Singleton
	@LessonsDataStore
	fun provideLessonsDataStore(
		@ApplicationContext context: Context
	): DataStore<Preferences> = PreferenceDataStoreFactory.create(
		produceFile = { context.preferencesDataStoreFile(Constants.LESSONS_PREFS_NAME) }
	)

	@Provides
	@Singleton
	@NotesDataStore
	fun provideNotesDataStore(
		@ApplicationContext context: Context
	): DataStore<Preferences> = PreferenceDataStoreFactory.create(
		produceFile = { context.preferencesDataStoreFile(Constants.NOTES_PREFS_NAME) }
	)

	@Provides
	@Singleton
	@MapsDataStore
	fun provideMapsDataStore(
		@ApplicationContext context: Context
	): DataStore<Preferences> = PreferenceDataStoreFactory.create(
		produceFile = { context.preferencesDataStoreFile(Constants.MAPS_PREFS_NAME) }
	)
}