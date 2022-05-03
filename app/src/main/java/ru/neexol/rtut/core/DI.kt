package ru.neexol.rtut.core

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.neexol.rtut.data.LessonsDataSource
import ru.neexol.rtut.data.sources.api.API
import ru.neexol.rtut.data.sources.api.LessonsAPIDataSource
import ru.neexol.rtut.domain.repositories.LessonsRepository
import ru.neexol.rtut.domain.usecases.GetGroupLessons
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
class DI {
	@Provides
	@Singleton
	fun provideGetGroupLessons(repository: LessonsRepository) = GetGroupLessons(repository)

	@Provides
	@Singleton
	fun provideLessonsRepository(dataSource: LessonsDataSource) = LessonsRepository(dataSource)

	@Provides
	@Singleton
	fun provideLessonsDataSource(api: API): LessonsDataSource = LessonsAPIDataSource(api)


	@Provides
	@Singleton
	fun provideApi(retrofit: Retrofit): API = retrofit.create(API::class.java)

	@Provides
	@Singleton
	fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
		.baseUrl(Constants.BASE_URL)
		.client(client)
		.addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
		.build()

	@Provides
	@Singleton
	fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient = OkHttpClient().newBuilder()
		.addInterceptor(interceptor)
		.build()

	@Provides
	@Singleton
	fun provideInterceptor() = Interceptor {
		val requestBuilder = it.request().newBuilder()
		// requestBuilder.addHeader(name ,  value)
		it.proceed(requestBuilder.build())
	}
}