package ru.neexol.rtut.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.neexol.rtut.domain.models.GroupLessons
import ru.neexol.rtut.domain.models.Lesson
import ru.neexol.rtut.domain.models.LessonTime
import javax.inject.Inject

class LessonsPrefsManager @Inject constructor(
	private val dataStore: DataStore<Preferences>
) {
	private companion object {
		val GROUP = stringPreferencesKey("group")
		val CHECKSUM = stringPreferencesKey("checksum")
		val LESSONS = stringPreferencesKey("lessons")
		val TIMES = stringPreferencesKey("times")
	}

	private suspend fun <T> get(key: Preferences.Key<T>) = dataStore.data.map { it[key] }.first()
	suspend fun getGroup() = get(GROUP) ?: "ИКБО-12-19"
	suspend fun getChecksum() = get(CHECKSUM)
	suspend fun getLessons() = get(LESSONS)?.let { Json.decodeFromString<List<Lesson>>(it) }
	suspend fun getTimes() = get(TIMES)?.let { Json.decodeFromString<List<LessonTime>>(it) }

	private suspend fun <T> put(key: Preferences.Key<T>, value: T) {
		dataStore.edit { it[key] = value }
	}
	private suspend fun putGroup(group: String) = put(GROUP, group)
	private suspend fun putChecksum(checksum: String) = put(CHECKSUM, checksum)
	private suspend fun putLessons(lessons: List<Lesson>) = put(LESSONS, Json.encodeToString(lessons))
	suspend fun putGroupLessons(groupLessons: GroupLessons) = groupLessons.apply {
		putGroup(group)
		putChecksum(checksum)
		putLessons(lessons)
	}
	suspend fun putTimes(times: List<LessonTime>)  = put(TIMES, Json.encodeToString(times))
}