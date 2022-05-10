package ru.neexol.rtut.core.di.data.maps

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
import ru.neexol.rtut.core.di.annotations.MapsDataStore
import ru.neexol.rtut.data.maps.local.MapsCacheManager
import ru.neexol.rtut.data.maps.local.MapsLocalDataSource
import ru.neexol.rtut.data.maps.local.MapsPrefsManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapsLocalDI {
	@Provides
	@Singleton
	fun provideDataSource(
		prefsManager: MapsPrefsManager,
		cacheManager: MapsCacheManager,
	): MapsLocalDataSource = MapsLocalDataSource(prefsManager, cacheManager)

	@Provides
	@Singleton
	fun providePrefsManager(
		@MapsDataStore dataStore: DataStore<Preferences>
	): MapsPrefsManager = MapsPrefsManager(dataStore)

	@Provides
	@Singleton
	@MapsDataStore
	fun provideDataStore(
		@ApplicationContext context: Context
	): DataStore<Preferences> = PreferenceDataStoreFactory.create(
		produceFile = { context.preferencesDataStoreFile(Constants.MAPS_PREFS_NAME) }
	)

	@Provides
	@Singleton
	fun provideCacheManager(
		@ApplicationContext context: Context
	): MapsCacheManager = MapsCacheManager(context)
}