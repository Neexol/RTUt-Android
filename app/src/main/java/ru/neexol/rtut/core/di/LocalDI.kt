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
import ru.neexol.rtut.data.local.LessonsLocalDataSource
import ru.neexol.rtut.data.local.LessonsPrefsManager
import ru.neexol.rtut.data.remote.API
import ru.neexol.rtut.data.remote.LessonsRemoteDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDI {
	@Provides
	@Singleton
	fun provideLessonsLocalDataSource(
		manager: LessonsPrefsManager
	) = LessonsLocalDataSource(manager)

	@Provides
	@Singleton
	fun provideLessonsPrefsManager(
		dataStore: DataStore<Preferences>
	) = LessonsPrefsManager(dataStore)

	@Provides
	@Singleton
	fun provideLessonsPrefs(
		@ApplicationContext context: Context
	) = PreferenceDataStoreFactory.create(
		produceFile = { context.preferencesDataStoreFile(Constants.LESSONS_PREFS_NAME) }
	)
}