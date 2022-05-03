package ru.neexol.rtut.core.di

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
import ru.neexol.rtut.core.Constants
import ru.neexol.rtut.data.sources.api.API
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDI {
	@Provides
	@Singleton
	fun provideApi(retrofit: Retrofit): API = retrofit.create(API::class.java)

	@ExperimentalSerializationApi
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