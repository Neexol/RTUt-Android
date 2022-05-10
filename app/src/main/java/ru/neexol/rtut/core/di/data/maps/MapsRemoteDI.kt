package ru.neexol.rtut.core.di.data.maps

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import ru.neexol.rtut.core.Constants
import ru.neexol.rtut.core.di.annotations.MapsRetrofit
import ru.neexol.rtut.data.maps.remote.MapsAPI
import ru.neexol.rtut.data.maps.remote.MapsRemoteDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapsRemoteDI {
	@Provides
	@Singleton
	fun provideDataSource(
		api: MapsAPI
	): MapsRemoteDataSource = MapsRemoteDataSource(api)

	@Provides
	@Singleton
	fun provideApi(
		@MapsRetrofit retrofit: Retrofit
	): MapsAPI = retrofit.create()

	@ExperimentalSerializationApi
	@Provides
	@Singleton
	@MapsRetrofit
	fun provideRetrofit(): Retrofit = Retrofit.Builder()
		.baseUrl(Constants.BASE_URL)
		.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
		.build()
}