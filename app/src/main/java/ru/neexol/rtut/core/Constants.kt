package ru.neexol.rtut.core

import ru.neexol.rtut.domain.models.LessonTime

object Constants {
	const val BASE_URL = "http://192.168.31.177/"
	const val LESSONS_PREFS_NAME = "lessons_prefs"

	val DEFAULT_TIMES = listOf(
		LessonTime("09-00", "10-30"),
		LessonTime("10-40", "12-10"),
		LessonTime("12-40", "14-10"),
		LessonTime("14-20", "15-50"),
		LessonTime("16-20", "17-50"),
		LessonTime("18-00", "19-30"),
		LessonTime("19-40", "21-10"),
		LessonTime("18-30", "20-00"),
		LessonTime("20-10", "21-40")
	)
}