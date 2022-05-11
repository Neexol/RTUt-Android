package ru.neexol.rtut.core.di

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
import ru.neexol.rtut.data.lessons.remote.LessonsApi
import ru.neexol.rtut.data.maps.remote.MapsApi
import ru.neexol.rtut.data.notes.remote.NotesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
	@Provides
	@Singleton
	fun provideLessonsApi(retrofit: Retrofit): LessonsApi = retrofit.create()

	@Provides
	@Singleton
	fun provideNotesApi(retrofit: Retrofit): NotesApi = retrofit.create()

	@Provides
	@Singleton
	fun provideMapsApi(retrofit: Retrofit): MapsApi = retrofit.create()

	@ExperimentalSerializationApi
	@Provides
	@Singleton
	fun provideRetrofit(): Retrofit = Retrofit.Builder()
		.baseUrl(Constants.BASE_URL)
		.addConverterFactory(ScalarsConverterFactory.create())
		.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
		.build()
}