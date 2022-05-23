package ru.neexol.rtut.presentation.screens.teacher.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import ru.neexol.rtut.R
import ru.neexol.rtut.presentation.components.ImagePlaceholder
import ru.neexol.rtut.presentation.components.LessonItem
import ru.neexol.rtut.presentation.screens.teacher.TeacherUiState

@ExperimentalMaterialApi
@Composable
internal fun LessonsList(
	uiState: TeacherUiState,
	week: Int
) {
	when {
		uiState.isLessonsLoading -> {
			Box(Modifier.fillMaxSize()) {
				CircularProgressIndicator(Modifier.align(Alignment.Center))
			}
		}
		uiState.lessons == null -> {
			ImagePlaceholder(
				iconId = R.drawable.ic_search_24,
				labelId = R.string.search_holder_label
			)
		}
		uiState.lessons[week].flatten().isEmpty() -> {
			ImagePlaceholder(
				iconId = R.drawable.ic_fail_search_24,
				labelId = R.string.fail_search
			)
		}
		else -> {
			val daysLabels = stringArrayResource(R.array.days).toList()
			LazyColumn(
				modifier = Modifier.fillMaxSize(),
				contentPadding = PaddingValues(vertical = 14.dp, horizontal = 20.dp),
				verticalArrangement = Arrangement.spacedBy(14.dp),
			) {
				uiState.lessons[week].forEachIndexed { day, dayLessons ->
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
							LessonItem(lesson, uiState.times!![lesson.number])
						}
					}
				}
			}
		}
	}
}