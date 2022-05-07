package ru.neexol.rtut.core.di.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import ru.neexol.rtut.core.Constants
import ru.neexol.rtut.core.di.annotations.LessonsRetrofit
import ru.neexol.rtut.data.remote.lessons.LessonsAPI
import ru.neexol.rtut.data.remote.lessons.LessonsRemoteDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LessonsRemoteDI {
	@Provides
	@Singleton
	fun provideDataSource(
		api: LessonsAPI
	): LessonsRemoteDataSource = LessonsRemoteDataSource(api)

	@Provides
	@Singleton
	fun provideApi(
		@LessonsRetrofit retrofit: Retrofit
	): LessonsAPI = retrofit.create()

	@ExperimentalSerializationApi
	@LessonsRetrofit
	@Provides
	@Singleton
	fun provideRetrofit(): Retrofit = Retrofit.Builder()
		.baseUrl(Constants.LESSONS_URL)
		.addConverterFactory(ScalarsConverterFactory.create())
		.addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
		.build()
}