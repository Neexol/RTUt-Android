package ru.neexol.rtut.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.*
import java.util.*

object Utils {
	suspend inline fun <T> DataStore<Preferences>.get(key: Preferences.Key<T>) = data.map {
		it[key]
	}.first()
	suspend inline fun <T> DataStore<Preferences>.put(key: Preferences.Key<T>, value: T) {
		edit { it[key] = value }
	}

	suspend inline fun FlowCollector<Resource.Loading>.emitLoading() = emit(Resource.Loading)
	suspend inline fun <T> FlowCollector<Resource.Success<T>>.emitSuccess(value: T) = emit(Resource.Success(value))
	suspend inline fun FlowCollector<Resource.Failure>.emitFailure(cause: Throwable) = emit(Resource.Failure(cause))

	inline fun <T> resourceFlowOf(crossinline block: suspend () -> T) = flow {
		emitLoading()
		emitSuccess(block())
	}.catch {
		emitFailure(it)
	}

	fun getDayAndWeek(): Pair<Int, Int> {
		val start = GregorianCalendar(2022, 2 - 1, 10)
		val current = GregorianCalendar()
		var day = current.get(Calendar.DAY_OF_WEEK) - 1
		var week = current.get(Calendar.WEEK_OF_YEAR) - start.get(Calendar.WEEK_OF_YEAR) + 1
		if (day == 0) {
			day++
			week++
		}
		return --day to --week
	}
}