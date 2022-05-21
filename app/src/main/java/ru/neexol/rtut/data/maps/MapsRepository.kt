package ru.neexol.rtut.data.maps

import android.graphics.Bitmap
import android.graphics.Canvas
import com.caverock.androidsvg.SVG
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.core.Utils.emitFailure
import ru.neexol.rtut.core.Utils.emitLoading
import ru.neexol.rtut.core.Utils.emitSuccess
import ru.neexol.rtut.data.maps.local.MapsLocalDataSource
import ru.neexol.rtut.data.maps.remote.MapsRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.ceil

@Singleton
class MapsRepository @Inject constructor(
	private val localDataSource: MapsLocalDataSource,
	private val remoteDataSource: MapsRemoteDataSource
) {
	data class GetMapsResult(
		val floor: Int,
		val classroom: String,
		val maps: List<Bitmap>
	)

	private fun String.withHyphen(): String {
		return if (isNotBlank() && get(0).isLetter() && !contains('-')) {
			indexOfFirst { it.isDigit() }.takeIf { it != -1 }?.let {
				"${take(it)}-${drop(it)}"
			} ?: this
		} else this
	}

	private fun bitmap(map: String): Bitmap {
		val svg = SVG.getFromString(map).apply {
			documentHeight = documentViewBox.height() * 4
			documentWidth = documentViewBox.width() * 4
		}
		val bitmap = Bitmap.createBitmap(
			ceil(svg.documentWidth.toDouble()).toInt(),
			ceil(svg.documentHeight.toDouble()).toInt(),
			Bitmap.Config.ARGB_8888
		)
		svg.renderToCanvas(Canvas(bitmap))
		return bitmap
	}

	private fun bitmap(rawMap: String, classroom: String): Pair<Bitmap, Bitmap?> {
		var found = false
		val map = if (classroom.isNotEmpty()) {
			val index = rawMap.indexOf("\"$classroom\"".uppercase())
			found = index != -1
			if (found) {
				StringBuilder(rawMap).apply {
					setCharAt(index + classroom.length + 14, '0')
				}.toString()
			} else rawMap
		} else rawMap

		return bitmap(map) to if (found) bitmap(rawMap) else null
	}

	fun getMaps(classroom: String) = flow {
		emitLoading()
		val localMaps = localDataSource.getMaps()?.also {
			emitSuccess(it)
		}
		if (classroom.isEmpty()) {
			val localMapsInfo = localDataSource.getMapsInfo()
			val remoteMapsInfo = remoteDataSource.getMapsInfo()
			if (localMaps == null || localMapsInfo?.date != remoteMapsInfo.date) {
				coroutineScope {
					remoteMapsInfo.maps.map {
						async {
							localDataSource.putMap(it.file, remoteDataSource.getMapStream(it.file))
						}
					}.awaitAll().also {
						localDataSource.putMapsInfo(remoteMapsInfo)
						emitSuccess(it)
					}
				}
			}
		}
	}.catch { cause ->
		emitFailure(cause)
		localDataSource.getMaps()?.also {
			emitSuccess(it)
		}
	}.transform { resource ->
		when (resource) {
			is Resource.Success -> {
				coroutineScope {
					resource.data.map { file ->
						println(classroom.withHyphen())
						async { bitmap(file.readText(), classroom.withHyphen()) }
					}.awaitAll()
				}.let { markedBitmaps ->
					val floor = if (classroom.isNotEmpty()) {
						markedBitmaps.indexOfFirst { it.second != null }
					} else 0
					if (floor != -1) {
						val highlighted = GetMapsResult(floor, classroom, markedBitmaps.map { it.first })
						if (classroom.isNotEmpty()) {
							val results = listOf(
								highlighted,
								highlighted.copy(maps = markedBitmaps.map { it.second ?: it.first })
							)
							emitSuccess(results[0])
							repeat(5) {
								delay(200)
								emitSuccess(results[it % 2])
							}
						}
						emitSuccess(highlighted)
					} else {
						emitFailure(Exception("Not found"))
					}
				}
			}
			is Resource.Failure -> emitFailure(resource.cause)
			Resource.Loading -> emitLoading()
		}
	}.flowOn(Dispatchers.IO)
}