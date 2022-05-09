package ru.neexol.rtut.core.di.data.notes

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import ru.neexol.rtut.core.Constants
import ru.neexol.rtut.core.di.annotations.NotesRetrofit
import ru.neexol.rtut.data.notes.remote.NotesAPI
import ru.neexol.rtut.data.notes.remote.NotesRemoteDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotesRemoteDI {
	@Provides
	@Singleton
	fun provideDataSource(
		api: NotesAPI
	): NotesRemoteDataSource = NotesRemoteDataSource(api)

	@Provides
	@Singleton
	fun provideApi(
		@NotesRetrofit retrofit: Retrofit
	): NotesAPI = retrofit.create()

	@ExperimentalSerializationApi
	@Provides
	@Singleton
	@NotesRetrofit
	fun provideRetrofit(): Retrofit = Retrofit.Builder()
		.baseUrl(Constants.BASE_URL)
		.addConverterFactory(ScalarsConverterFactory.create())
		.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
		.build()
}