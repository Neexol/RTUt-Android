package ru.neexol.rtut.presentation.screens.schedule

import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.data.lessons.models.LessonTime

data class ScheduleUiState(
	val lessons: List<List<List<Lesson?>>>? = null,
	val isLessonsLoading: Boolean = false,
	val times: List<LessonTime>? = null,
	val message: String? = null
)