package ru.neexol.rtut.presentation.screens.teacher

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import ru.neexol.rtut.R
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.data.lessons.models.LessonTime
import ru.neexol.rtut.presentation.components.FindTopBar
import ru.neexol.rtut.presentation.components.LessonItem
import ru.neexol.rtut.presentation.components.PagerTopBar

@ExperimentalPagerApi
@Composable
fun TeacherScreen(vm: TeacherViewModel) {
	val uiState = vm.uiState
	val weekPagerState = rememberPagerState(vm.dayWeek.second)

	Column {
		WeekPagerBar(weekPagerState)
		FindTeacherBar(vm)
		if (!uiState.lessons.isNullOrEmpty() && !uiState.times.isNullOrEmpty()) {
			LessonsList(uiState.lessons, uiState.times, weekPagerState.currentPage)
		}
	}
}

@ExperimentalPagerApi
@Composable
private fun WeekPagerBar(state: PagerState) {
	PagerTopBar(
		state = state,
		title = stringResource(R.string.week_letter),
		items = (1..16).map(Int::toString),
		isLast = true
	)
}

@Composable
private fun FindTeacherBar(vm: TeacherViewModel) {
	FindTopBar(
		value = vm.teacher,
		placeholder = stringResource(R.string.teacher),
		onValueChange = { vm.teacher = it.trimStart() },
		onImeAction = { vm.fetchLessons() }
	)
}

@Composable
private fun LessonsList(lessons: List<List<List<Lesson>>>, times: List<LessonTime>, week: Int) {
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