package ru.neexol.rtut.core.di

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
import ru.neexol.rtut.data.remote.LessonsAPI
import ru.neexol.rtut.data.remote.LessonsRemoteDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDI {
	@Provides
	@Singleton
	fun provideLessonsRemoteDataSource(api: LessonsAPI) = LessonsRemoteDataSource(api)

	@Provides
	@Singleton
	fun provideLessonsApi(@LessonsRetrofit retrofit: Retrofit): LessonsAPI = retrofit.create()

	@ExperimentalSerializationApi
	@LessonsRetrofit
	@Provides
	@Singleton
	fun provideLessonsRetrofit(): Retrofit = Retrofit.Builder()
		.baseUrl(Constants.LESSONS_URL)
		.addConverterFactory(ScalarsConverterFactory.create())
		.addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
		.build()
}