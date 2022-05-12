package ru.neexol.rtut.presentation.screens.grouplessons

import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.data.lessons.models.LessonTime

data class GroupLessonsUiState(
	val lessons: List<List<List<Lesson?>>>? = null,
	val isLessonsLoading: Boolean = false,
	val times: List<LessonTime>? = null,
	val message: String? = null
)