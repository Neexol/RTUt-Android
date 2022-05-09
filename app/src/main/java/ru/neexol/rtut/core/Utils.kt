package ru.neexol.rtut.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.*

object Utils {
	suspend fun <T> DataStore<Preferences>.get(key: Preferences.Key<T>) = data.map {
		it[key]
	}.first()
	suspend fun <T> DataStore<Preferences>.put(key: Preferences.Key<T>, value: T) {
		edit { it[key] = value }
	}

	suspend inline fun FlowCollector<Resource.Loading>.emitLoading() = emit(Resource.Loading)
	suspend inline fun <T> FlowCollector<Resource.Success<T>>.emitSuccess(value: T) = emit(Resource.Success(value))
	suspend inline fun FlowCollector<Resource.Error>.emitError(cause: Throwable) = emit(Resource.Error(cause))

	inline fun <T> resourceFlowOf(crossinline block: suspend () -> T) = flow {
		emitLoading()
		emitSuccess(block())
	}.catch {
		emitError(it)
	}
}