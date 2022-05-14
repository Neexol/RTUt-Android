package ru.neexol.rtut.data.maps

import android.graphics.Bitmap
import android.graphics.Canvas
import com.caverock.androidsvg.SVG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
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

	private fun bitmap(rawMap: String, classroom: String): Pair<Boolean, Bitmap> {
		var found = false
		val map = if (classroom.isNotEmpty()) {
			val index = rawMap.indexOf("\"$classroom\"".uppercase())
			found = index != -1
			if (found) {
				StringBuilder(rawMap).apply {
					setCharAt(index + classroom.length + 14, '6')
				}.toString()
			} else rawMap
		} else rawMap

		return found to bitmap(map)
	}

	fun getMaps(classroom: String) = flow {
		emitLoading()
		val localMaps = localDataSource.getMaps()?.also {
			emitSuccess(it)
		}
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
						async { bitmap(file.readText(), classroom) }
					}.awaitAll()
				}.let { markedBitmaps ->
					val floor = markedBitmaps.indexOfFirst {
						it.first
					}.takeIf {
						classroom.isNotEmpty()
					} ?: 0
					if (floor != -1) {
						emitSuccess(GetMapsResult(floor, classroom, markedBitmaps.map { it.second }))
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