package ru.neexol.rtut.data.maps.remote

import javax.inject.Inject

class MapsRemoteDataSource @Inject constructor(
	private val api: MapsAPI
) {
	suspend fun getMapsInfo() = api.getMapsInfo()
	suspend fun getMapStream(fileName: String) = api.getMap(fileName).byteStream()
}