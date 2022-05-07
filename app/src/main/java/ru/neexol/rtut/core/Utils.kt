package ru.neexol.rtut.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object Utils {
	suspend fun <T> DataStore<Preferences>.get(key: Preferences.Key<T>) = data.map {
		it[key]
	}.first()
	suspend fun <T> DataStore<Preferences>.put(key: Preferences.Key<T>, value: T) {
		edit { it[key] = value }
	}
}