package ru.neexol.rtut.core.di.data.lessons

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
import ru.neexol.rtut.core.di.annotations.LessonsRetrofit
import ru.neexol.rtut.data.lessons.remote.LessonsAPI
import ru.neexol.rtut.data.lessons.remote.LessonsRemoteDataSource
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
	@Provides
	@Singleton
	@LessonsRetrofit
	fun provideRetrofit(): Retrofit = Retrofit.Builder()
		.baseUrl(Constants.BASE_URL)
		.addConverterFactory(ScalarsConverterFactory.create())
		.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
		.build()
}