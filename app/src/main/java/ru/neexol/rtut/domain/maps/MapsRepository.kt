package ru.neexol.rtut.domain.maps

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
import kotlinx.coroutines.flow.map
import ru.neexol.rtut.core.Utils.emitFailure
import ru.neexol.rtut.core.Utils.emitLoading
import ru.neexol.rtut.core.Utils.emitSuccess
import ru.neexol.rtut.core.Utils.resourceFlowOf
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
	data class FindMapResult(
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
		val index = rawMap.indexOf("\"$classroom\"".uppercase())
		val map = if (index != -1) {
			StringBuilder(rawMap).apply {
				setCharAt(index + classroom.length + 14, '6')
			}.toString()
		} else rawMap
		return (index != -1) to bitmap(map)
	}

	fun getMaps() = flow {
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
				}.awaitAll().let {
					localDataSource.putMapsInfo(remoteMapsInfo)
					emitSuccess(it)
				}
			}
		}
	}.catch {
		emitFailure(it)
		localDataSource.getMaps()?.also { maps ->
			emitSuccess(maps)
		}
	}.map { resource ->
		resource.map {
			coroutineScope {
				it.map { file ->
					async { bitmap(file.readText()) }
				}.awaitAll()
			}
		}
	}.flowOn(Dispatchers.IO)

	fun findClassroom(classroom: String) = resourceFlowOf {
		coroutineScope {
			localDataSource.getMaps()!!.map { file ->
				async {
					bitmap(file.readText(), classroom)
				}
			}.awaitAll().let { markedBitmaps ->
				val floor = markedBitmaps.indexOfFirst { it.first }
				if (floor != -1) {
					FindMapResult(
						markedBitmaps.indexOfFirst { it.first },
						classroom,
						markedBitmaps.map { it.second }
					)
				} else throw Exception("Classroom not found")
			}
		}
	}.flowOn(Dispatchers.IO)
}