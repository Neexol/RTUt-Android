package ru.neexol.rtut.data.maps.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming
import ru.neexol.rtut.data.maps.models.MapsInfo

interface MapsAPI {
	@GET("/static/maps/info.json")
	suspend fun getMapsInfo(): MapsInfo

	@Streaming
	@GET("/static/maps/{file}")
	suspend fun getMap(@Path("file") fileName: String): ResponseBody
}