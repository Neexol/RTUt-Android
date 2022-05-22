package ru.neexol.rtut.presentation.screens.teacher.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import ru.neexol.rtut.R
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.data.lessons.models.LessonTime
import ru.neexol.rtut.presentation.components.LessonItem

@ExperimentalMaterialApi
@Composable
internal fun LessonsList(lessons: List<List<List<Lesson>>>, times: List<LessonTime>, week: Int) {
	val daysLabels = stringArrayResource(R.array.days).toList()
	LazyColumn(
		modifier = Modifier.fillMaxSize(),
		contentPadding = PaddingValues(vertical = 14.dp, horizontal = 20.dp),
		verticalArrangement = Arrangement.spacedBy(14.dp),
	) {
		lessons[week].forEachIndexed { day, dayLessons ->
			if (dayLessons.isNotEmpty()) {
				item {
					Text(
						text = daysLabels[day],
						style = MaterialTheme.typography.h6,
						modifier = Modifier
							.padding(vertical = 8.dp, horizontal = 16.dp)
							.padding(top = 8.dp)
					)
				}
				items(dayLessons) { lesson ->
					LessonItem(lesson, times[lesson.number])
				}
			}
		}
	}
}