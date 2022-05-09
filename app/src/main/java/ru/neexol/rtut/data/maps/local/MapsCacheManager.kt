package ru.neexol.rtut.data.maps.local

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class MapsCacheManager @Inject constructor(
	private val context: Context
) {
	suspend fun getMap(fileName: String) = withContext(Dispatchers.IO) {
		File(context.cacheDir, fileName).takeIf { it.exists() }?.toString()
	}
	suspend fun putMap(fileName: String, input: InputStream) = withContext(Dispatchers.IO) {
		File(context.cacheDir, fileName).apply {
			input.use {
				outputStream().use {
					input.copyTo(it)
				}
			}
		}.toString()
	}
}