package ru.neexol.rtut.domain.models

data class LessonsWithTimes(
	val lessons: List<Lesson>,
	val times: List<LessonTime>
)