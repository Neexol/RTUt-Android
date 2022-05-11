package ru.neexol.rtut.data.maps.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapsCacheManager @Inject constructor(
	@ApplicationContext private val context: Context
) {
	init { File(context.cacheDir, "maps").mkdir() }

	private fun mapFile(fileName: String) = File(context.cacheDir, "maps/$fileName")

	suspend fun getMap(fileName: String) = withContext(Dispatchers.IO) {
		mapFile(fileName).takeIf { it.exists() }?.toString()
	}
	suspend fun putMap(fileName: String, input: InputStream) = withContext(Dispatchers.IO) {
		mapFile(fileName).apply {
			input.use {
				outputStream().use {
					input.copyTo(it)
				}
			}
		}.toString()
	}
}