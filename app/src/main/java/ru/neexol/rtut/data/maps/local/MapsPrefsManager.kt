package ru.neexol.rtut.data.maps.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.neexol.rtut.core.Utils.get
import ru.neexol.rtut.core.Utils.put
import ru.neexol.rtut.data.maps.models.MapsInfo
import javax.inject.Inject

class MapsPrefsManager @Inject constructor(
	private val dataStore: DataStore<Preferences>
) {
	private companion object {
		val MAPS_INFO = stringPreferencesKey("info")
	}

	suspend fun getMapsInfo() = dataStore.get(MAPS_INFO)?.let { Json.decodeFromString<MapsInfo>(it) }
	suspend fun putMapsInfo(info: MapsInfo) = dataStore.put(MAPS_INFO, Json.encodeToString(info))
}