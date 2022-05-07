package ru.neexol.rtut.core.di.data.lessons

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
import ru.neexol.rtut.data.lessons.local.LessonsLocalDataSource
import ru.neexol.rtut.data.lessons.local.LessonsPrefsManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LessonsLocalDI {
	@Provides
	@Singleton
	fun provideDataSource(
		manager: LessonsPrefsManager
	): LessonsLocalDataSource = LessonsLocalDataSource(manager)

	@Provides
	@Singleton
	fun providePrefsManager(
		@LessonsDataStore dataStore: DataStore<Preferences>
	): LessonsPrefsManager = LessonsPrefsManager(dataStore)

	@Provides
	@Singleton
	@LessonsDataStore
	fun provideDataStore(
		@ApplicationContext context: Context
	): DataStore<Preferences> = PreferenceDataStoreFactory.create(
		produceFile = { context.preferencesDataStoreFile(Constants.LESSONS_PREFS_NAME) }
	)
}