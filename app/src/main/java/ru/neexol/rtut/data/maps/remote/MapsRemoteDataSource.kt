package ru.neexol.rtut.data.maps.remote

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapsRemoteDataSource @Inject constructor(
	private val api: MapsApi
) {
	suspend fun getMapsInfo() = api.getMapsInfo()
	suspend fun getMapStream(fileName: String) = api.getMap(fileName).byteStream()
}