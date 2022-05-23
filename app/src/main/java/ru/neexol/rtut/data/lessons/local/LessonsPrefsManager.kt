package ru.neexol.rtut.data.lessons.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.neexol.rtut.core.Utils.get
import ru.neexol.rtut.core.Utils.put
import ru.neexol.rtut.core.di.annotations.LessonsDataStore
import ru.neexol.rtut.data.lessons.models.DEFAULT_TIMES
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.data.lessons.models.LessonTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LessonsPrefsManager @Inject constructor(
	@LessonsDataStore private val dataStore: DataStore<Preferences>
) {
	private companion object {
		val GROUP = stringPreferencesKey("group")
		val CHECKSUM = stringPreferencesKey("checksum")
		val LESSONS = stringPreferencesKey("lessons")
		val TIMES = stringPreferencesKey("times")
	}

	suspend fun getGroup() = dataStore.get(GROUP)
	suspend fun putGroup(group: String) = dataStore.put(GROUP, group)

	suspend fun getChecksum() = dataStore.get(CHECKSUM)
	suspend fun putChecksum(checksum: String) = dataStore.put(CHECKSUM, checksum)

	suspend fun getLessons() = dataStore.get(LESSONS)?.let {
		Json.decodeFromString<List<Lesson>>(it)
	}
	suspend fun putLessons(lessons: List<Lesson>) = dataStore.put(LESSONS, Json.encodeToString(lessons))

	suspend fun getTimes() = dataStore.get(TIMES)?.let {
		Json.decodeFromString<List<LessonTime>>(it.replace('-',':'))
	} ?: DEFAULT_TIMES
	suspend fun putTimes(times: List<LessonTime>) = dataStore.put(TIMES, Json.encodeToString(times))
}