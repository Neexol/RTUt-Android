package ru.neexol.rtut.data.maps.local

import ru.neexol.rtut.data.maps.models.MapsInfo
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapsLocalDataSource @Inject constructor(
	private val prefsManager: MapsPrefsManager,
	private val cacheManager: MapsCacheManager
) {
	suspend fun getMapsInfo() = prefsManager.getMapsInfo()
	suspend fun putMapsInfo(info: MapsInfo) = prefsManager.putMapsInfo(info)

	suspend fun getMaps(): List<String>? = prefsManager.getMapsInfo()?.let { mapsInfo ->
		mapsInfo.maps.mapNotNull {
			cacheManager.getMap(it.file)
		}.takeIf {
			it.size == mapsInfo.maps.size
		}
	}
	suspend fun putMap(fileName: String, input: InputStream) = cacheManager.putMap(fileName, input)
}