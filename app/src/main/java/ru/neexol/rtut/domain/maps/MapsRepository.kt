package ru.neexol.rtut.domain.maps

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.neexol.rtut.core.Utils.emitError
import ru.neexol.rtut.core.Utils.emitLoading
import ru.neexol.rtut.core.Utils.emitSuccess
import ru.neexol.rtut.data.maps.local.MapsLocalDataSource
import ru.neexol.rtut.data.maps.remote.MapsRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapsRepository @Inject constructor(
	private val localDataSource: MapsLocalDataSource,
	private val remoteDataSource: MapsRemoteDataSource
) {
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
		emitError(Exception("Не удалось синхронизировать карты"))
		localDataSource.getMaps()?.also {
			emitSuccess(it)
		}
	}.flowOn(Dispatchers.IO)
}